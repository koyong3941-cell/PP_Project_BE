package com.kh.pp.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.board.model.dao.AdminCategoryMapper;
import com.kh.pp.board.model.dto.CategoryDto;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.InvalidCategoryException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class AdminCategoryService {
	private final AdminCategoryMapper adminCategoryMapper;

	// Create
	@Transactional
	public void saveCategory(String categoryName) {
		if (categoryName == null || categoryName.trim().isEmpty()) {
			throw new InvalidCategoryException("카테고리 이름을 적어주세요.");
		}
		
		int result = adminCategoryMapper.saveCategory(categoryName);
		
		if (result < 1) {
			throw new FailSaveException("카테고리 추가에 실패했습니다.");
		}
		
	}

	// Read
	public PageResponse<CategoryDto> findCategoryAll(int page, int size) {
		int offset = page * size;

		int totalElements = adminCategoryMapper.getCategoryTotalElements();
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<CategoryDto> categorys = adminCategoryMapper.findCategoryAll(offset, size);
		
		return new PageResponse<>(categorys, totalElements, page, size);
	}
}
