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

import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.service.BoardService;
import com.kh.pp.common.api.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<BoardDto>>> findBoardAll(@RequestParam(value = "page", defaultValue ="0")  int page){
		List<BoardDto> boards = boardService.findBoardAll(page);
		
		return ResponseEntity.ok(ApiResponse.success(boards));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveBoard(@AuthenticationPrincipal @ModelAttribute @Valid BoardDto board){
		boardService.saveBoard(board);
		return ResponseEntity.status(201).body(ApiResponse.created(null));
	}
	
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<ApiResponse<Void>> deleteBoard(@AuthenticationPrincipal @PathVariable(name = "boardNo") Long boardNo){
		boardService.deleteBoard(boardNo);
		return ResponseEntity.status(204).body(ApiResponse.created("deleted", null));
	}
	
	@PatchMapping
	public ResponseEntity<ApiResponse<Void>> editBoard(@AuthenticationPrincipal @ModelAttribute  @Valid BoardDto board){
		boardService.editBoard(board);
		return ResponseEntity.status(200).body(ApiResponse.created("edited", null));
	}

}
