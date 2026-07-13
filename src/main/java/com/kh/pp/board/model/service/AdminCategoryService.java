package com.kh.pp.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.board.model.dao.AdminCategoryMapper;
import com.kh.pp.board.model.dto.CategoryDto;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.InvalidCategoryException;
import com.kh.pp.exception.PlantNotFoundException;

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
	public void saveCategory(CategoryDto category) {
		String categoryName = category.getCategoryName();
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

	public PageResponse<CategoryDto> findCategoryByKeyword(int page, int size, String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return findCategoryAll(page, size);
		}
		
		int offset = page * size;
		
		List<String> keywordList = new ArrayList<>();
		String[] words = keyword.trim().split("\\s+");
		for (String word : words) {
			if (!word.isEmpty()) {
				keywordList.add(word);
			}
		}
		
		int totalElements = adminCategoryMapper.getCategoryTotalElementsByKeyword(keywordList);
		
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<CategoryDto> categorys = adminCategoryMapper.findCategoryByKeyword(offset, size, keywordList);
	
		return new PageResponse<>(categorys, totalElements, page, size);
	}
	
	// Delete
	@Transactional
	public int deleteCategory(List<Long> categoryNos) {
		if (categoryNos == null || categoryNos.isEmpty()) {
	        throw new PlantNotFoundException("삭제할 식물 번호를 선택해주세요.");
	    }
		
		int result = adminCategoryMapper.deleteCategory(categoryNos);
		
		if (result == 0) {
			throw new FailDeleteException("삭제에 실패하였습니다.");
		}
		return result;
	}
}
