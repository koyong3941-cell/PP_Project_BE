package com.kh.pp.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.notice.model.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
	@Select("""
			SELECT 
				n.NOTICE_NO
				,m.MEMBER_NO
				,n.NOTICE_TITLE
				,n.NOTICE_CONTENT
				,n.NOTICE_COUNT
				,n.CREATE_DATE
				,n.DEL_YN
			FROM
				NOTICE n
			JOIN
				MEMBER m ON (n.MEMBER_NO = m.MEMBER_NO)
			WHERE
				n.DEL_YN = 'N'
				ORDER BY n.NOTICE_NO DESC
				OFFSET #{offset} ROWS
				FETCH NEXT #{limit} ROWS ONLY
			""")
	List<NoticeDto> findNoticeAll(@Param("offset") int offset, @Param("limit") int limit);
	
	@Update("UPDATE NOTICE SET NOTICE_COUNT = NOTICE_COUNT + 1 WHERE NOTICE_NO = #{noticeNo}")
	void increaseCount(Long noticeNo);
	
	NoticeDto noticeDetail(Long noticeNo);

}
