package com.kh.pp.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.board.model.dto.BoardDto;

@Mapper
public interface AdminBoardMapper {
	
	// Read
	List<BoardDto> findBoardAll(@Param("offset") int offset, @Param("size") int size);
	
	List<BoardDto> findBoardByKeyword(
			@Param("offset") int offset
			, @Param("size") int size
			, @Param("keywordList") List<String> keywordList
			, @Param("target") String target);
	
	// Update
	int restoreBoards(@Param("boardNos") List<Long> boardNos);

	// Delete
	int deleteBoards(@Param("boardNos") List<Long> boardNos);
	
	// Count
	int getBoardTotalElements();

	int getBoardTotalElementsByKeyword(
			@Param("keywordList") List<String> keywordList
			, @Param("target") String target);



}
