package com.kh.pp.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.board.model.dao.BoardMapper;
import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.dto.Category;
import com.kh.pp.board.model.vo.Board;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper boardMapper;
	
	public List<BoardDto> findBoardAll(int page) {
	    int offset = page * 10;
	    int limit = 10;
	    
	    return boardMapper.findBoardAll(offset, limit);
	}

	@Transactional
	public void saveBoard(BoardDto board) {
		validateBoard(board);
		Board boardEntity = Board.builder()
				.memberNo(board.getMemberNo())
				.boardTitle(board.getBoardTitle())
				.boardContent(board.getBoardContent())
				.categoryNo(board.getCategoryNo())
				// 이미지 나중에 추가 작업자 성현
				.build();	
		
		boardMapper.saveBoard(boardEntity);
	}
	
	private void validateBoard(BoardDto board) {
		if (board.getBoardTitle() == null || board.getBoardTitle().isEmpty()) {
			throw new FailSaveException("제목은 필수입니다.");
		}
		if (board.getBoardContent() == null || board.getBoardContent().isEmpty()) {
			throw new FailSaveException("내용은 필수입니다.");
		}
	}
	
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
	
	public BoardDto boardDetail(Long boardNo) {
		increaseCount(boardNo);
		BoardDto board = getBoardNoOrThrow(boardNo);
		
		return board;
	}
	
	private void increaseCount(Long boardNo) {
		boardMapper.increaseCount(boardNo);
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
