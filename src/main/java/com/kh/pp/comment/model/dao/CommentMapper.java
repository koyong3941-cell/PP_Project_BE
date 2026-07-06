package com.kh.pp.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.comment.model.dto.CommentDto;

@Mapper
public interface CommentMapper {
	
	// 댓글 전체 조회
	@Select("""
			SELECT 
				C.COMMENT_NO
				,M.MEMBER_NAME
				,C.MEMBER_NO
				,C.BOARD_NO
				,C.COMMENT_CONTENT
				,C.CREATE_DATE
				,C.DEL_YN
			FROM
				BOARD_COMMENT C
			LEFT 
				JOIN 
					MEMBER M
			  ON 
			  	M.MEMBER_NO = C.MEMBER_NO
			WHERE
				BOARD_NO = #{boardNo}
			""")
	List<CommentDto> findCommentByBoardNo(Long boardNo);

	// 댓글 생성
	@Insert("""
			INSERT
				INTO
					BOARD_COMMENT
					(
					BOARD_NO
					,MEMBER_NO
					,COMMENT_CONTENT
					,CREATE_DATE
					,DEL_YN
					)
				VALUES
					(
					#{boardNo}
					,#{memberNo}
					,#{commentContent}
					,SYSDATE
					,'N'
					)
			""")
	int saveComment(CommentDto comment);

	// 댓글 수정
	@Update("""
			UPDATE
				BOARD_COMMENT
			SET
				COMMENT_CONTENT = #{commentContent}
			WHERE
				MEMBER_NO = #{memberNo}
			AND
				COMMENT_NO = #{commentNo}
			""")
	int editComment(CommentDto comment);

	// 댓글 삭제
	@Update("""
			UPDATE
				BOARD_COMMENT
			SET
				DEL_YN = 'Y'
			WHERE
				MEMBER_NO = #{memberNo}
			AND
				COMMENT_NO = #{commentNo}
			""")
	int DeleteComment(CommentDto comment);


	
	

}
