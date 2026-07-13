package com.kh.pp.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.notice.model.dto.AdminNoticeDto;
import com.kh.pp.notice.model.vo.Notice;

@Mapper
public interface AdminNoticeMapper {

	@Select("""
			SELECT
				COUNT(*)
			FROM
				NOTICE
			""")
	int getNoticeTotalElements();

	@Select("""
			SELECT
				N.NOTICE_NO
				,M.MEMBER_ID
				,M.MEMBER_NAME
				,N.NOTICE_TITLE
				,N.CREATE_DATE
				,N.DEL_YN
			FROM
				NOTICE N
			LEFT JOIN
				MEMBER M ON N.MEMBER_NO = M.MEMBER_NO
			ORDER BY N.NOTICE_NO DESC
			OFFSET 
				#{offset} ROWS
			FETCH 
				NEXT #{size} ROWS ONLY
			""")
	List<AdminNoticeDto> findNoticeAll(@Param("offset")int offset, @Param("size")int size);

	@Update("""
			UPDATE 
				NOTICE 
			  SET 
		   		NOTICE_COUNT = NOTICE_COUNT + 1 
			WHERE 
		 		NOTICE_NO = #{noticeNo}
			""")
	void increaseNoticeCount(Long noticeNo);

	@Select("""
			SELECT
			N.NOTICE_NO
			 ,N.NOTICE_TITLE
			 ,N.NOTICE_CONTENT
			 ,N.NOTICE_COUNT
			 ,M.MEMBER_ID
			 ,M.MEMBER_NAME
			 ,N.CREATE_DATE
		  FROM 
				NOTICE N
		  JOIN
				MEMBER M USING(MEMBER_NO)
		 WHERE 
				N.NOTICE_NO = #{noticeNo}
			""")
	AdminNoticeDto noticeDetail(Long noticeNo);

	@Select("""
			SELECT 
				n.NOTICE_NO
				,m.MEMBER_NAME
				,n.NOTICE_TITLE
				,n.NOTICE_COUNT
				,n.CREATE_DATE
				,(SELECT 
						IMG_PATH || SAVE_NAME 
             	   FROM 
             	   		MEMBER_IMG 
             	  WHERE 
             	  		MEMBER_NO = n.MEMBER_NO 
               	    AND 
               	    	DEL_YN = 'N') AS PROFILE_IMAGE
			FROM
				NOTICE n
			JOIN
				MEMBER m ON (n.MEMBER_NO = m.MEMBER_NO)
			WHERE
				n.DEL_YN = 'N'
			AND
				(
					 m.MEMBER_NAME   LIKE '%' || #{keyword} || '%'
		             OR n.NOTICE_TITLE  LIKE '%' || #{keyword} || '%'
		             OR n.NOTICE_CONTENT LIKE '%' || #{keyword} || '%'
				)
				ORDER BY n.NOTICE_NO DESC
				OFFSET #{offset} ROWS
				FETCH NEXT #{size} ROWS ONLY
			""")
	List<AdminNoticeDto> searchNotice(@Param("keyword") String keyword, @Param("offset") int offset, @Param("size") int size);

	@Insert("""
			INSERT
			  INTO
			    NOTICE(
				MEMBER_NO,
				NOTICE_TITLE,
				NOTICE_CONTENT
			   )
				VALUES
			   	(
				#{memberNo}
			   	,#{noticeTitle}
			   	,#{noticeContent}
			   	)
			""")
	int saveNotice(Notice noticeEntity);

	@Select("""
			SELECT
				NOTICE_NO
			FROM
			  		NOTICE
			WHERE
			 		MEMBER_NO = #{memberNo}
			ORDER
			 	BY
			    NOTICE_NO DESC
			FETCH
			 	FIRST 1 ROWS ONLY
		""")
	Long getLastNoticeNoByMemberNo(Long noticeNo);

	@Update("""
	    UPDATE
	    	 NOTICE
	    SET
	       NOTICE_TITLE = #{noticeTitle}
	      ,NOTICE_CONTENT = #{noticeContent}
	    WHERE
	      NOTICE_NO = #{noticeNo}
			""")
	int editNotices(Notice noticeEntity);

	@Update("""
		    <script>
		        UPDATE NOTICE
		        SET DEL_YN = 'Y'
		        WHERE NOTICE_NO IN
		        <foreach item="item" collection="noticeNos" open="(" separator="," close=")">
		            #{item}
		        </foreach>
		    </script>
		""")
	int deleteNotices(@Param("noticeNos")List<Long> noticeNos);
	
	
}
