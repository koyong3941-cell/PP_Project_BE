package com.kh.pp.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.board.model.dao.BoardMapper;
import com.kh.pp.comment.model.dao.CommentMapper;
import com.kh.pp.comment.model.dto.CommentDto;
import com.kh.pp.comment.model.dto.CommentLikeDto;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.FailUpdateException;
import com.kh.pp.exception.FailUserRequestException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
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
		if(result != 1) {
			throw exception;
		}
	}
	
	private void isActiveBoard(int isActive, RuntimeException exception) {
		if(isActive < 1) {
			throw exception;
		}
	}
	// 좋아요
	@Transactional
	public void commentLike(Long memberNo, Long commentNo) {
		commentLikeValidate(memberNo, commentNo);
		
		int result = commentMapper.commentLike(memberNo, commentNo);
		
		if(result != 1) {
			throw new FailUserRequestException("좋아요 요청에 실패하였습니다.");
		}
	}

	// 좋아요 원복
	@Transactional
	public void commentLikeAbort(Long memberNo, Long commentNo) {		
		int result = commentMapper.commentLikeAbort(memberNo, commentNo);
		
		if(result != 1) {
			throw new FailUserRequestException("취소 요청에 실패하였습니다.");
		}
	}
	
	// 좋아요 유무 검증
	private void commentLikeValidate(Long memberNo, Long commentNo) {
		int result = commentMapper.commentLikeValidate(memberNo, commentNo);
		
		if(result > 0) {
			throw new FailUserRequestException("이미 좋아요 한 상태로 좋아요에 실패하였습니다.");
		}
	}

	public CommentLikeDto commentLikeAllByCommentNo(Long commentNo) {
		CommentLikeDto commentLike = commentMapper.commentLikeAllByCommentNo(commentNo);
		
		if (commentLike == null) {
	        CommentLikeDto dto = new CommentLikeDto();
	        dto.setCommentLikeCount(0L);
	        return dto;
	    }
		
		return commentLike;
	}
	
}
