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
import com.kh.pp.board.model.dto.Category;
import com.kh.pp.board.model.service.BoardService;
import com.kh.pp.common.api.ApiResponse;

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
		int memberNoFromToken = userDetails.getMemberNo();
		board.setMemberNo(memberNoFromToken);
		 
		boardService.saveBoard(board);
		return ResponseEntity.status(201).body(ApiResponse.created(null));
	}
	
	// Read
	@GetMapping
	public ResponseEntity<ApiResponse<List<BoardDto>>> findBoardAll(@RequestParam(value = "page", defaultValue ="0") int page){
		List<BoardDto> boards = boardService.findBoardAll(page);
		
		return ResponseEntity.ok(ApiResponse.success(boards));
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<BoardDto>>> findBoardByKeyword(@RequestParam(name = "page", defaultValue = "0") int page,
																		  @RequestParam(name = "keyword", required = false) String keyword,
																		  @RequestParam(name = "target", required = false) String target){
		List<BoardDto> boards = boardService.findBoardByKeyword(page, keyword, target);
		
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
		int memberNoFromToken = userDetails.getMemberNo();
		boardService.editBoard(board, memberNoFromToken, boardNo);
		return ResponseEntity.status(200).body(ApiResponse.success("edited", null));
	}
	
	// Delete
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> deleteBoard(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable(name = "boardNo") Long boardNo){
		int memberNoFromToken = userDetails.getMemberNo();
		boardService.deleteBoard(boardNo, memberNoFromToken);
		return ResponseEntity.status(200).body(ApiResponse.created("deleted", null));
	}
	
	// 기타
	@GetMapping("/category")
	public ResponseEntity<ApiResponse<List<Category>>> findCategoryAll() {
		List<Category> category = boardService.categoryInfo();
		return ResponseEntity.ok(ApiResponse.success(category));
	}
}
