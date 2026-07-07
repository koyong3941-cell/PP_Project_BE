package com.kh.pp.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.dto.BoardReactionDto;
import com.kh.pp.board.model.dto.Category;
import com.kh.pp.board.model.vo.Board;

@Mapper
public interface BoardMapper {

	// Create
	int saveBoard(Board boardEntity);

	// Read
	List<BoardDto> findBoardAll(@Param("offset") int offset, @Param("size") int size);

	List<BoardDto> findBoardByKeyword(
		@Param("offset") int offset 
		, @Param("size") int size
		, @Param("keywordList") List<String> keywordList
		, @Param("target") String target
	);
	
	BoardDto boardDetail(Long boardNo);

	List<Category> boardCategoryAll();

	Long getLastBoardNoByMemberNo(Long memberNo);
	
	int getBoardTotalElements();
	
	int getBoardTotalElementsByKeyword(
			@Param("keywordList") List<String> keywordList
			, @Param("target") String target);
	
	// Update
	int editBoard(Board boardEntity);
	
	void increaseBoardCount(Long boardNo);

	// Delete
	int deleteBoard(@Param("boardNo") Long boardNo, @Param("memberNo") Long memberNo);
	
	//커멘트 좋아요 Update
	int addBoardLike(@Param("memberNo") Long memberNo, @Param("boardNo") Long boardNo);

	int addBoardDislike(@Param("memberNo") Long memberNo, @Param("boardNo") Long boardNo);

	int validateLikeExists(@Param("memberNo") Long memberNo, @Param("boardNo") Long boardNo);

	int validateDislikeExists(@Param("memberNo") Long memberNo, @Param("boardNo") Long boardNo);

	@Select("""
			SELECT
				COUNT(*) AS boardLike
			FROM
				BOARD_LIKE
			WHERE
				BOARD_NO = #{boardNo}
			""")
	int findBoardLikeReactions(Long boardNo);
	
	@Select("""
			SELECT
				COUNT(*) AS boardLike
			FROM
				BOARD_DISLIKE
			WHERE
				BOARD_NO = #{boardNo}
			""")
	int findBoardDisLikeReactions(Long boardNo);



}
