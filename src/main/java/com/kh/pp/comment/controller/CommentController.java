package com.kh.pp.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.comment.model.dto.CommentDto;
import com.kh.pp.comment.model.service.CommentService;
import com.kh.pp.common.api.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardNo}/comments")
@Slf4j
public class CommentController {
	private final CommentService commentService;
	
	// 댓글 조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<CommentDto>>> findCommentByBoardNo(@PathVariable(name="boardNo") Long boardNo) {
		List<CommentDto> commentList =  commentService.findCommentByBoardNo(boardNo);
		
		return ResponseEntity.status(200).body(ApiResponse.success("조회 성공", commentList));
	}
	
	// 댓글 작성
	@PostMapping
	public ResponseEntity<ApiResponse<CommentDto>> saveComment(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CommentDto comment
																,@PathVariable(name="boardNo") Long boardNo) {
		comment.setBoardNo(boardNo);
		comment.setMemberNo(userDetails.getMemberNo());
		commentService.saveComment(comment);
		
		return ResponseEntity.status(201).body(ApiResponse.success("작성 성공", comment));
	}
	
	
	// 댓글 수정*api/boards/{boardNo}/comments/{commentNo}
	@PatchMapping("{commentNo}")
	public ResponseEntity<ApiResponse<CommentDto>> editComment(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CommentDto comment
																,@PathVariable(name="boardNo") Long boardNo,@PathVariable(name="commentNo") Long commentNo) {
		comment.setBoardNo(boardNo);
		comment.setCommentNo(commentNo);
		comment.setMemberNo(userDetails.getMemberNo());
		commentService.editComment(comment);
		
		return ResponseEntity.status(200).body(ApiResponse.success("댓글 수정 완료", comment));
	}
	// 댓글 삭제
	@DeleteMapping("{commentNo}")
	public ResponseEntity<ApiResponse<Void>> DeleteComment(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable(name="boardNo") Long boardNo,@PathVariable(name="commentNo") Long commentNo){
		CommentDto comment = new CommentDto();
		
		comment.setMemberNo(userDetails.getMemberNo());
		comment.setBoardNo(boardNo);
		comment.setCommentNo(commentNo);
		
		commentService.DeleteComment(comment);
		return ResponseEntity.status(204).body(ApiResponse.noContent("댓글 수정 완료", null));
	}
	
	// 댓글 좋아요 삭제* /api//boards/{boardNo}/comments/{commentNo}/like
	@PostMapping("{commentNo}/like")
	public ResponseEntity<ApiResponse<CommentDto>> commentLike(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable(name="commentNo") Long commentNo) {
		Long memberNoFromToken = userDetails.getMemberNo();
		commentService.commentLike(memberNoFromToken, commentNo);
		
		return ResponseEntity.status(200).body(ApiResponse.success("좋아요 완료", null));
	}
	
	// 댓글 좋아요 삭제
	@DeleteMapping("{commentNo}/like")
	public ResponseEntity<ApiResponse<CommentDto>> commentLikeAbort(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable(name="commentNo") Long commentNo) {
		Long memberNoFromToken = userDetails.getMemberNo();

		commentService.commentLikeAbort(memberNoFromToken, commentNo);
		
		return ResponseEntity.status(200).body(ApiResponse.success("좋아요 취소 완료", null));
	}
}
