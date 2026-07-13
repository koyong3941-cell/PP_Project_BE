package com.kh.pp.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.dto.BoardReactionDto;
import com.kh.pp.board.model.dto.CategoryDto;
import com.kh.pp.board.model.service.BoardService;
import com.kh.pp.board.model.vo.Category;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
	private final BoardService boardService;

	// Create
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveBoard(@AuthenticationPrincipal CustomUserDetails userDetails,
													   @ModelAttribute @Valid BoardDto board){
		Long memberNoFromToken = userDetails.getMemberNo();
		board.setMemberNo(memberNoFromToken);
		 
		boardService.saveBoard(board);
		return ResponseEntity.status(201).body(ApiResponse.created(null));
	}
	
	// Read
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<BoardDto>>> findBoardAll(@RequestParam(value = "page", defaultValue ="0") int page){
		PageResponse<BoardDto> boards = boardService.findBoardAll(page);
		
		return ResponseEntity.ok(ApiResponse.success(boards));
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<BoardDto>>> findBoardByKeyword(@RequestParam(name = "page", defaultValue = "0") int page,
																		  @RequestParam(name = "keyword", required = false) String keyword,
																		  @RequestParam(name = "target", required = false) String target){
		PageResponse<BoardDto> boards = boardService.findBoardByKeyword(page, keyword, target);
		
		return ResponseEntity.ok(ApiResponse.success(boards));
	}
	
	@GetMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<BoardDto>> boardDetail(@PathVariable(name = "boardNo") Long boardNo){
		
		BoardDto board = boardService.boardDetail(boardNo);
		
		return ResponseEntity.status(200).body(ApiResponse.success(board));
	}
	
	// Update
	@PatchMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> editBoard(@AuthenticationPrincipal CustomUserDetails userDetails, 
													   @ModelAttribute  @Valid BoardDto board,
													   @PathVariable(name = "boardNo") Long boardNo){
		Long memberNoFromToken = userDetails.getMemberNo();
		board.setMemberNo(memberNoFromToken);
		board.setBoardNo(boardNo);
		boardService.editBoard(board);
		return ResponseEntity.status(200).body(ApiResponse.success("edited", null));
	}
	
	// Delete
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> deleteBoard(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable(name = "boardNo") Long boardNo){
		Long memberNoFromToken = userDetails.getMemberNo();
		boardService.deleteBoard(boardNo, memberNoFromToken);
		return ResponseEntity.status(204).body(ApiResponse.created("deleted", null));
	}
	
	// 카테고리 관련
	@GetMapping("/category")
	public ResponseEntity<ApiResponse<List<CategoryDto>>> findBoardCategoryAll() {
		List<CategoryDto> category = boardService.boardCategoryAll();
		return ResponseEntity.ok(ApiResponse.success(category));
	}
	
	// 게시글 좋아요
	@PostMapping("/{boardNo}/like")
	public ResponseEntity<ApiResponse<Void>> addBoardLike(@AuthenticationPrincipal CustomUserDetails userDetails,
															@PathVariable(name = "boardNo") Long boardNo){
		Long memberNoFromToken = userDetails.getMemberNo();
		 
		boardService.addBoardLike(memberNoFromToken, boardNo);
		return ResponseEntity.status(200).body(ApiResponse.created(null));
	}
	
	// 게시글 싫어요
	@PostMapping("/{boardNo}/dislike")
	public ResponseEntity<ApiResponse<Void>> addBoardDislike(@AuthenticationPrincipal CustomUserDetails userDetails,
															@PathVariable(name = "boardNo") Long boardNo){
		Long memberNoFromToken = userDetails.getMemberNo();
		 
		boardService.addBoardDislike(memberNoFromToken, boardNo);
		return ResponseEntity.status(200).body(ApiResponse.created(null));
	}
	
	@GetMapping("/{boardNo}/reactions")
	public ResponseEntity<ApiResponse<BoardReactionDto>> findBoardReactions(@PathVariable(name = "boardNo") Long boardNo){
		BoardReactionDto reactions = boardService.findBoardReactions(boardNo);
		
		return ResponseEntity.status(200).body(ApiResponse.created("조회 성공", reactions));
	}

}
