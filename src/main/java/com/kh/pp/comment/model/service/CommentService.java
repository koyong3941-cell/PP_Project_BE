package com.kh.pp.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.pp.board.model.dao.BoardMapper;
import com.kh.pp.comment.model.dao.CommentMapper;
import com.kh.pp.comment.model.dto.CommentDto;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.FailUpdateException;
import com.kh.pp.exception.FailUserRequestException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
	private final CommentMapper commentMapper;

	// 코멘트 리스트 조회 
	public List<CommentDto> findCommentByBoardNo(Long boardNo) {
		List<CommentDto> list = commentMapper.findCommentByBoardNo(boardNo);
		
		if(list == null) {
			throw new FailUserRequestException("코멘트 요청에 실패하였습니다.");
		}
		return list;
	}

	// 코멘트 작성 및 저장
	public void saveComment(CommentDto comment) {
		int isActive = commentMapper.isActiveBoard(comment.getBoardNo());
		
		isActiveBoard(isActive, new FailSaveException("코멘트 생성 요청에 실패하였습니다."));
		
		int result = commentMapper.saveComment(comment);
		
		checkAffectedRows(result, new FailSaveException("코멘트 생성 요청에 실패하였습니다."));
	}

	public void editComment(CommentDto comment) {
		int isActive = commentMapper.isActiveBoard(comment.getBoardNo());
		
		isActiveBoard(isActive, new FailSaveException("코멘트 생성 요청에 실패하였습니다."));
		
		int result = commentMapper.editComment(comment);
		
		checkAffectedRows(result, new FailUpdateException("코멘트 수정 요청에 실패하였습니다."));
	}

	public void DeleteComment(CommentDto comment) {
		int result = commentMapper.DeleteComment(comment);
		
		checkAffectedRows(result, new FailDeleteException("코멘트 삭제 요청에 실패하였습니다."));
	}
	
	//공통 행 익셉션 처리
	private void checkAffectedRows(int result, RuntimeException exception) {
		if(result < 1) {
			throw exception;
		}
	}
	
	private void isActiveBoard(int isActive, RuntimeException exception) {
		if(isActive < 1) {
			throw exception;
		}
	}
	
}
