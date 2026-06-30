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
	void saveBoard(Board boardEntity);

	Long getLastBoardNoByMemberNo(int memberNo);
	
	// Read
	List<BoardDto> findBoardAll(@Param("offset") int offset, @Param("limit") int limit);

	List<BoardDto> findBoardByKeyword(@Param("offset") int offset, @Param("limit") int limit, @Param("keyword") String keyword);

	List<BoardDto> findBoardByMemberName(@Param("offset") int offset, @Param("limit") int limit, @Param("keyword") String keyword);

	
	
	BoardDto boardDetail(Long boardNo);

	// Update
	void editBoard(@Param("board")BoardDto board, @Param("memberNo") int memberNo, Long boardNo);
	
	void increaseCount(Long boardNo);

	List<Category> categoryInfo();

	BoardDto findByNo(Long boardNo);
	
	// Delete
	int deleteBoard(@Param("boardNo") Long boardNo, @Param("memberNo") int memberNo);


}
