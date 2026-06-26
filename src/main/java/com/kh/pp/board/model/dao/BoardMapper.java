package com.kh.pp.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.board.model.dto.BoardDto;

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
	
}
