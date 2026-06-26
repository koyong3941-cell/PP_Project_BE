package com.kh.pp.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.pp.board.model.dao.BoardMapper;
import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.vo.Board;
import com.kh.pp.exception.FailSaveException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper boardMapper;
	
	public List<BoardDto> findBoardAll(int page) {
	    int offset = page * 10;
	    int limit = 10;
	    
	    return boardMapper.findBoardAll(offset, limit);
	}

	public void saveBoard(BoardDto board) {
		validateBoard(board);
		
		Board boardEntity = Board.builder()
				.boardTitle(board.getBoardTitle())
				.boardContent(board.getBoardContent())
				// 이미지 나중에 추가
				
				.build();
				
	}
	
	private void validateBoard(BoardDto board) {
		if (board.getBoardTitle() == null || board.getBoardTitle().isEmpty()) {
			throw new FailSaveException("제목은 필수입니다.");
		}
	}
}
