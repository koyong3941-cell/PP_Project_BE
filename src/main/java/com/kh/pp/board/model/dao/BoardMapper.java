package com.kh.pp.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.dto.Category;
import com.kh.pp.board.model.vo.Board;

@Mapper
public interface BoardMapper {

	// Create
	int saveBoard(Board boardEntity);

	Long getLastBoardNoByMemberNo(Long memberNo);
	
	// Read
	List<BoardDto> findBoardAll(@Param("offset") int offset, @Param("limit") int limit);

	List<BoardDto> findBoardByKeyword(
		@Param("offset") int offset 
		, @Param("limit") int limit
		, @Param("keywordList") List<String> keywordList
		, @Param("target") String target
	);
	
	BoardDto boardDetail(Long boardNo);

	// Update
	int editBoard(@Param("board")BoardDto board, @Param("memberNo") Long memberNo, Long boardNo);
	
	void increaseCount(Long boardNo);

	List<Category> categoryInfo();

	BoardDto findByNo(Long boardNo);
	
	// Delete
	int deleteBoard(@Param("boardNo") Long boardNo, @Param("memberNo") Long memberNo);




}
