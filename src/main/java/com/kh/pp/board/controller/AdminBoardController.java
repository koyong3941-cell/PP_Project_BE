package com.kh.pp.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.dto.BoardNoListDto;
import com.kh.pp.board.model.service.AdminBoardService;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/boards")
public class AdminBoardController {
	private final AdminBoardService adminBoardService;
	
	// Read
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<BoardDto>>> findBoardAll(
			@RequestParam(value = "page", defaultValue ="0") int page
			, @RequestParam(name = "size", defaultValue = "10") int size
			){
		PageResponse<BoardDto> boards = adminBoardService.findBoardAll(page, size);
		
		return ResponseEntity.status(200).body(ApiResponse.success(boards));
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<BoardDto>>> findBoardByKeyword(
			@RequestParam(name = "page", defaultValue = "0") int page
			, @RequestParam(name = "size", defaultValue = "10") int size
			, @RequestParam(name = "keyword", required = false) String keyword
			, @RequestParam(name = "target", required = false) String target
			){
		PageResponse<BoardDto> boards = adminBoardService.findBoardByKeyword(page, size, keyword, target);
		
		return ResponseEntity.ok(ApiResponse.success(boards));
	}
	
	// Update
	@PatchMapping
	public ResponseEntity<ApiResponse<?>> restoreBoards(@RequestBody BoardNoListDto request){
		int result = adminBoardService.restoreBoards(request.getBoardNos());
		return ResponseEntity.ok(ApiResponse.success(result + "개 복구에 성공했습니다.", result));
	}
	
	// Delete
	@DeleteMapping
	public ResponseEntity<ApiResponse<?>> deletePlants(@RequestBody BoardNoListDto request){
		int result = adminBoardService.deleteBoards(request.getBoardNos());
		return ResponseEntity.ok(ApiResponse.success(result + "개 삭제에 성공했습니다.", result));
	}
}
