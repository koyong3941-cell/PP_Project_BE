package com.kh.pp.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.dto.Category;
import com.kh.pp.board.model.vo.Board;

@Mapper
public interface BoardMapper {
	@Select("""
			SELECT 
				b.BOARD_NO
				,c.CATEGORY_NAME
				,m.MEMBER_NAME
				,b.BOARD_TITLE
				,b.BOARD_CONTENT
				,b.COUNT
				,b.CREATE_DATE
				,b.DEL_YN
			FROM
				BOARD b
			JOIN
				CATEGORY c USING(CATEGORY_NO)
			JOIN
				MEMBER m USING(MEMBER_NO)
			WHERE
				b.DEL_YN = 'N'
				ORDER BY b.BOARD_NO DESC
				OFFSET #{offset} ROWS
				FETCH NEXT #{limit} ROWS ONLY
			""")
	List<BoardDto> findBoardAll(@Param("offset") int offset, @Param("limit") int limit);


	int deleteBoard(@Param("boardNo") Long boardNo, @Param("memberNo") int memberNo);
	
	@Select("""
			SELECT CASE WHEN EXISTS(SELECT 1 FROM BOARD WHERE MEMBER_NO = #{memberNo}) THEN 1 ELSE 0 END AS EXISTS_YN FROM DUAL
			""")
	boolean existsByUserId(String memberNo);
	
	BoardDto boardDetail(Long boardNo);

	@Update("UPDATE BOARD SET COUNT = COUNT + 1 WHERE BOARD_NO = #{boardNo}")
	void increaseCount(Long boardNo);
	
	@Select("""
			SELECT 
				 CATEGORY_NO
				, CATEGORY_NAME
			FROM 
				CATEGORY
				""")
	List<Category> categoryInfo();

	BoardDto findByNo(Long boardNo);

	@Insert("""
			INSERT
			  INTO
		  		BOARD	
		  		(
		  		MEMBER_NO 
			  	,BOARD_TITLE
			  	,BOARD_CONTENT
			  	,CATEGORY_NO
			  		)
			VALUES
					(
				#{memberNo}
				,#{boardTitle}
				,#{boardContent}
				,#{categoryNo}
					)
		""")
	void saveBoard(Board boardEntity);

	void editBoard(@Param("board")BoardDto board,@Param("memberNo") int memberNo, Long boardNo);
	
}
