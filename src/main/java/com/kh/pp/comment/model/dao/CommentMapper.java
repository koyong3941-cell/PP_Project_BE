package com.kh.pp.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.comment.model.dto.CommentDto;
import com.kh.pp.comment.model.dto.CommentLikeDto;

@Mapper
public interface CommentMapper {
	
	// 댓글 전체 조회
	@Select("""
		    SELECT
	        C.COMMENT_NO,
	        M.MEMBER_NAME,
	        C.MEMBER_NO,
	        C.BOARD_NO,
	        C.COMMENT_CONTENT,
	        C.CREATE_DATE,
	        C.DEL_YN,
	        MI.IMG_PATH,
	        MI.SAVE_NAME,(
	            SELECT COUNT(*)
	            FROM COMMENT_LIKE CL
	            WHERE CL.COMMENT_NO = C.COMMENT_NO
        ) AS COMMENT_LIKE_COUNT
	    FROM
	        BOARD_COMMENT C
	    LEFT JOIN MEMBER M
	        ON M.MEMBER_NO = C.MEMBER_NO
	    LEFT JOIN MEMBER_IMG MI
	        ON MI.MEMBER_NO = M.MEMBER_NO
	       AND MI.DEL_YN = 'N'
	    WHERE
	        C.BOARD_NO = #{boardNo}
	    AND
	        C.DEL_YN = 'N'
	    ORDER BY
	        C.CREATE_DATE ASC
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

	@Select("""
			SELECT
				Count(*)
			FROM
				BOARD
			WHERE
				BOARD_NO = #{boardNo}
			AND
				DEL_YN = 'N'
			""")
	int isActiveBoard(Long boardNo);

	@Insert("""
			INSERT
				INTO
					COMMENT_LIKE
					(
					MEMBER_NO
					,COMMENT_NO
					)
			VALUES
				(
				#{memberNo}
				,#{commentNo}
				)
			""")
	int commentLike(@Param("memberNo") Long memberNo, @Param("commentNo")Long commentNo);

	@Delete("""
				DELETE
					FROM
						COMMENT_LIKE
					WHERE
						MEMBER_NO = #{memberNo}
					AND
						COMMENT_NO =#{commentNo}
			""")
	int commentLikeAbort(@Param("memberNo") Long memberNo, @Param("commentNo")Long commentNo);
	
	@Select("""
			SELECT
				COUNT(*)
			FROM
				COMMENT_LIKE
			WHERE
						MEMBER_NO = #{memberNo}
					AND
						COMMENT_NO =#{commentNo}
			""")
	int commentLikeValidate(@Param("memberNo") Long memberNo, @Param("commentNo")Long commentNo);

	@Select("""
			SELECT
				COUNT(*) AS commentLikeCount
			FROM
				COMMENT_LIKE
			WHERE
				COMMENT_NO = #{commentNo}
			""")
	CommentLikeDto commentLikeAllByCommentNo(Long commentNo);


	
	

}
