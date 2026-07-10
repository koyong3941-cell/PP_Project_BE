package com.kh.pp.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {

	List<NoticeDto> findNoticeAll(@Param("offset") int offset, @Param("size") int size);

	NoticeDto noticeDetail(Long noticeNo);
	
	List<NoticeDto> searchNotice(@Param("keyword")String keyword,@Param("offset")int offset,@Param("size") int size);
	
	int getNoticeTotalElements();
	
	int saveNotice(Notice noticeEntity);

	int editNotice(Notice noticeEntity);

	void deleteNotice(@Param("noticeNo")Long noticeNo);
	
	void updateCount(Long noticeNo);

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
	Long getLastNoticeNoByMemberNo(Long memberNo);
	
	void increaseNoticeCount(Long noticeNo);


}
