package com.kh.pp.board.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.board.model.dao.BoardImgMapper;
import com.kh.pp.board.model.dao.BoardMapper;
import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.dto.BoardImgDto;
import com.kh.pp.board.model.dto.Category;
import com.kh.pp.board.model.vo.Board;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper boardMapper;
	private final BoardImgMapper boardImgMapper;
	private final FileService fileService;
	
	// 보드 저장 및 검증 메서드
	@Transactional
	public void saveBoard(BoardDto board) {
		validateBoard(board);
		
		Board boardEntity = Board.builder()
				.memberNo(board.getMemberNo())
				.boardTitle(board.getBoardTitle())
				.boardContent(board.getBoardContent())
				.categoryNo(board.getCategoryNo())
				.build();	
		
		boardMapper.saveBoard(boardEntity);
		
		Long boardNo = boardMapper.getLastBoardNoByMemberNo(board.getMemberNo());
		
		log.info("생성된 boardNo : {}",boardNo);
		
		// 이미지 처리
	    if (board.getImageFiles() != null && !board.getImageFiles().isEmpty()) {
	        int order = 1;

	        for (MultipartFile file : board.getImageFiles()) {
	            if (!file.isEmpty()) {
	                try {
	                    String saveName = fileService.store(file, "board");

	                    BoardImgDto imgDto = new BoardImgDto();
	                    imgDto.setBoardNo(boardNo);
	                    imgDto.setOriginalName(file.getOriginalFilename());
	                    imgDto.setSaveName(saveName);
	                    imgDto.setImgPath("/uploads/board/");
	                    imgDto.setImgOrder(order++);

	                    boardImgMapper.insertBoardImg(imgDto);

	                } catch (Exception e) {
	                    log.error("이미지 저장 실패", e);
	                    throw new FailSaveException("이미지 저장 중 오류가 발생했습니다.");
	                }
	            }
	        }
	    }
	}
	
	private void validateBoard(BoardDto board) {
		if (board.getBoardTitle() == null || board.getBoardTitle().isEmpty()) {
			throw new FailSaveException("제목은 필수입니다.");
		}
		if (board.getBoardContent() == null || board.getBoardContent().isEmpty()) {
			throw new FailSaveException("내용은 필수입니다.");
		}
	}
	
	// Read
	public List<BoardDto> findBoardAll(int page) {
		int offset = page * 10;
		int limit = 10;
		
		return boardMapper.findBoardAll(offset, limit);
	}

	// 상세 및 리스트 조회
	public BoardDto boardDetail(Long boardNo) {
		increaseCount(boardNo);
		BoardDto board = getBoardNoOrThrow(boardNo);
		
		List<BoardImgDto> images = boardImgMapper.findByBoardNo(boardNo);
		board.setBoardImages(images);
		
		return board;
	}
	
	private void increaseCount(Long boardNo) {
		boardMapper.increaseCount(boardNo);
	}
	
	
	// 수정 및 삭제
	@Transactional
	public void deleteBoard(Long boardNo, int memberNo) {
		int result = boardMapper.deleteBoard(boardNo, memberNo);
		
		if (result < 1) {
			throw new FailDeleteException("삭제에 실패하였습니다.");
		}
	}
	
	@Transactional
	public void editBoard(BoardDto board, int memberNo, Long boardNo) {
		boardMapper.editBoard(board, memberNo, boardNo);
	}
	
	
	
	// ------ 접근 실패 시  ------	
	private BoardDto getBoardNoOrThrow(Long boardNo) {
		BoardDto boardDetail = boardMapper.boardDetail(boardNo);
		if (boardDetail == null) {
			throw new FailSaveException("유효하지 않은 접근입니다.");
		}
		return boardDetail; 

	}
	
	// ------ 카테고리 조회 검증 ----	
	public List<Category> categoryInfo() {
		return boardMapper.categoryInfo(); 
	}
	

}
