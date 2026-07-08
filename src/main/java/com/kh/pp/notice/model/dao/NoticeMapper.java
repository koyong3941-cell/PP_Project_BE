package com.kh.pp.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {

	List<NoticeDto> findNoticeAll(@Param("offset") int offset, @Param("limit") int limit);

	NoticeDto findByNoticeId(Long noticeNo);
	
	List<NoticeDto> Noticesearch(@Param("keyword")String keyword,@Param("offset")int offset,@Param("limit") int limit);
	
	int save(Notice noticeEntity);

	int editNotice(Notice noticeEntity);

	void deleteNotice(@Param("noticeNo")Long noticeNo);
	
	void updateCount(Long noticeNo);

	Long getLastNoticeNoByMemberNo(Long memberNo);



}
