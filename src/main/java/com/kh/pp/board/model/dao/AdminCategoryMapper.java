package com.kh.pp.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.board.model.dto.CategoryDto;

@Mapper
public interface AdminCategoryMapper {

	// Create
	int saveCategory(String categoryName);

	// Read
	List<CategoryDto> findCategoryAll(@Param("offset") int offset, @Param("size") int size);

	List<CategoryDto> findCategoryByKeyword(
			@Param("offset") int offset
			, @Param("size") int size
			, @Param("keywordList") List<String> keywordList
			);

	// Delete
	int deleteCategory(@Param("categoryNos") List<Long> categoryNos);

	// Count
	int getCategoryTotalElements();

	int getCategoryTotalElementsByKeyword(@Param("keywordList") List<String> keywordList);


	
}
