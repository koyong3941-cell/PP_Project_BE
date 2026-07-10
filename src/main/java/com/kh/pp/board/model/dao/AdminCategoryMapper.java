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

	// Count
	int getCategoryTotalElements();
	
}
