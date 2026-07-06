package com.kh.pp.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.board.model.dao.BoardImgMapper;
import com.kh.pp.board.model.dao.BoardMapper;
import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.dto.BoardImgDto;
import com.kh.pp.board.model.dto.Category;
import com.kh.pp.board.model.vo.Board;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.FailUpdateException;
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
	
	// Create
	@Transactional
	public void saveBoard(BoardDto board) {
		long count = validateBoardImages(board.getImageFiles());
		
		Board boardEntity = Board.builder()
				.memberNo(board.getMemberNo())
				.boardTitle(board.getBoardTitle())
				.boardContent(board.getBoardContent())
				.categoryNo(board.getCategoryNo())
				.build();	
		
		int result = boardMapper.saveBoard(boardEntity);
		
		if (result < 1) {
			throw new FailSaveException("작성에 실패했습니다.");
		}
		if (count > 0) {
			Long boardNo = boardMapper.getLastBoardNoByMemberNo(board.getMemberNo());
			
			saveBoardImg(boardNo, board.getImageFiles());
		}
	}
	

	// Read
	public PageResponse<BoardDto> findBoardAll(int page) {
		int size = 10;
		int offset = page * size;
		
		List<BoardDto> boards = boardMapper.findBoardAll(offset, size);
		
		long totalElements = boardMapper.getBoardtotalElements();
		
		return new PageResponse<>(boards, totalElements, page, size);
	}
	
	public PageResponse<BoardDto> findBoardByKeyword(int page, String keyword, String target) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return findBoardAll(page);
		}

		int size = 10;
		int offset = page * size;
		
		List<String> keywordList = new ArrayList<>();
		String[] words = keyword.trim().split("\\s+");
		for (String word : words) {
			if (!word.isEmpty()) {
				keywordList.add(word);
			}
		}
		
		if (target == null || target.trim().isEmpty()) {
			target = "all";
		}
		List<BoardDto> boards = boardMapper.findBoardByKeyword(offset, size, keywordList, target);
		// 앞단에서 카테고리별로 보이는 기능 추가하면 수정해야됨
		long totalElements = boardMapper.getBoardtotalElementsByKeyword(keywordList, target);
		
		return new PageResponse<>(boards, totalElements, page, size);
	}
	
	public BoardDto boardDetail(Long boardNo) {
		BoardDto board = boardMapper.boardDetail(boardNo);
		if (board == null) {
			throw new FailSaveException("해당 게시글이 존재하지 않습니다.");
		}
		
		List<BoardImgDto> images = boardImgMapper.findBoardImgByBoardNo(boardNo);
		board.setBoardImages(images);
		increaseBoardCount(boardNo);
		
		return board;
	}
	
	
	// Update
	@Transactional
	public void editBoard(BoardDto board) {
		long count = validateBoardImages(board.getImageFiles());

		Board boardEntity = Board.builder()
				.boardNo(board.getBoardNo())
				.memberNo(board.getMemberNo())
				.boardTitle(board.getBoardTitle())
				.boardContent(board.getBoardContent())
				.categoryNo(board.getCategoryNo())
				.build();
		
		int result = boardMapper.editBoard(boardEntity);
		
		if (result < 1) {
			throw new FailUpdateException("수정에 실패했습니다.");
		}
		// 기존 이미지 삭제처리 (DEL_YN을 'Y'로)
		boardImgMapper.deleteBoardImgByBoardNo(board.getBoardNo());
		
		if(count > 0) {
			saveBoardImg(board.getBoardNo(), board.getImageFiles());
		}
	}
	
	private void increaseBoardCount(Long boardNo) {
		boardMapper.increaseBoardCount(boardNo);
	}
	
	// Delete
	@Transactional
	public void deleteBoard(Long boardNo, Long memberNo) {
		int result = boardMapper.deleteBoard(boardNo, memberNo);
		
		if (result < 1) {
			throw new FailDeleteException("삭제에 실패하였습니다.");
		}
	}
	
	// ------ 카테고리 조회 검증 ------	
	public List<Category> boardCategoryAll() {
		return boardMapper.boardCategoryAll(); 
	}
	
	// ------ 이미지 갯수 확인 ------
	private long validateBoardImages(List<MultipartFile> imageFiles) {
		if (imageFiles == null) {
			return 0;
		}
		
		long count = imageFiles.stream()
							   .filter(file -> !file.isEmpty())
							   .count();
			
		if (count > 5) {
			throw new FailSaveException("이미지는 최대 5장까지 업로드할 수 있습니다.");
		}
		
		return count;
	}
	
	// ------ 게시글 이미지 저장 ------
	private void saveBoardImg(Long boardNo, List<MultipartFile> imageFiles) {
		if (imageFiles == null || imageFiles.isEmpty()) {
			return;
		}
	    int order = 1;

        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                try {
                    String saveName = fileService.store(file, "board");

                    BoardImgDto imgDto = new BoardImgDto();
                    imgDto.setBoardNo(boardNo);
                    imgDto.setOriginalName(file.getOriginalFilename());
                    imgDto.setSaveName(saveName);
                    imgDto.setImgPath("/uploads/board/");
                    imgDto.setImgOrder(order++);

                    int imgResult = boardImgMapper.insertBoardImg(imgDto);
	                    
                    if (imgResult < 1) {
                    	throw new FailSaveException("이미지 저장에 실패했습니다.");
                    }
                } catch (Exception e) {
                    log.error("이미지 저장 실패", e);
                    throw new FailSaveException("이미지 저장 중 오류가 발생했습니다.");
                }
            }
        }
	}
}
