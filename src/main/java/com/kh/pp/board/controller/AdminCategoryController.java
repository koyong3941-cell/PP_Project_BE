package com.kh.pp.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.board.model.dto.CategoryDto;
import com.kh.pp.board.model.dto.CategoryNoListDto;
import com.kh.pp.board.model.service.AdminCategoryService;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/category")
public class AdminCategoryController {
	private final AdminCategoryService adminCategoryService;
	
	// Create
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveCategory(@RequestBody @Valid CategoryDto category){
		adminCategoryService.saveCategory(category);
		return ResponseEntity.status(201).body(ApiResponse.created(null));
	}
	
	// Read
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<CategoryDto>>> findCategorysAll(
			@RequestParam(value = "page", defaultValue ="0") int page
			, @RequestParam(name = "size", defaultValue = "10") int size
			){
		PageResponse<CategoryDto> categorys = adminCategoryService.findCategoryAll(page, size);
	
		return ResponseEntity.status(200).body(ApiResponse.success(categorys));
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<CategoryDto>>> findCategoryByKeyword(
			@RequestParam(name = "page", defaultValue = "0") int page
			, @RequestParam(name = "size", defaultValue = "10") int size
			, @RequestParam(name = "keyword", required = false) String keyword
			){
		PageResponse<CategoryDto> categorys = adminCategoryService.findCategoryByKeyword(page, size, keyword);
		
		return ResponseEntity.ok(ApiResponse.success(categorys));
	}
	
	// Delete
	@DeleteMapping
	public ResponseEntity<ApiResponse<?>> deleteCategory(@RequestBody CategoryNoListDto request){
		int result = adminCategoryService.deleteCategory(request.getCategoryNos());
		return ResponseEntity.ok(ApiResponse.success(result + "개 삭제에 성공했습니다.", result));
	}
	
	
}
