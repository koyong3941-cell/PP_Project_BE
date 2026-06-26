package com.kh.pp.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public ResponseEntity<ApiResponse<Void>> saveBoard(@ModelAttribute @Valid BoardDto board
													  // ,@AuthenticationPrincipal 
			){
		boardService.saveBoard(board);
		return ResponseEntity.status(201).body(ApiResponse.created(null));
	}

}
