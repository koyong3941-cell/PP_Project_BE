package com.kh.pp.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {

	List<NoticeDto> findNoticeAll(@Param("offset") int offset, @Param("size") int size);

	NoticeDto NoticeDetail(Long noticeNo);
	
	List<NoticeDto> NoticeSearch(@Param("keyword")String keyword,@Param("offset")int offset,@Param("limit") int limit);
	
	int getNoticeTotalElements();
	
	int saveNotice(Notice noticeEntity);

	int editNotice(Notice noticeEntity);

	void deleteNotice(@Param("noticeNo")Long noticeNo);
	
	void updateCount(Long noticeNo);

	Long getLastNoticeNoByMemberNo(Long memberNo);
	
	void increaseNoticeCount(Long noticeNo);


}
