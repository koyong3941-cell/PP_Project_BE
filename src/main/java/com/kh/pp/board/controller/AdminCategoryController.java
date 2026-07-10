package com.kh.pp.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.board.model.dto.CategoryDto;
import com.kh.pp.board.model.service.AdminCategoryService;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/category")
public class AdminCategoryController {
	private final AdminCategoryService adminCategoryService;
	
	// Create
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveCategory(@RequestParam(name = "categoryName") String categoryName){
		adminCategoryService.saveCategory(categoryName);
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
	
	
}
