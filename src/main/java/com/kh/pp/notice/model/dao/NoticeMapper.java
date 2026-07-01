package com.kh.pp.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.notice.model.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
	List<NoticeDto> findNoticeAll(@Param("offset") int offset, @Param("limit") int limit);

	NoticeDto findById(Long noticeNo);
	
	List<NoticeDto> search(@Param("keyword")String keyword,@Param("offset")int offset,@Param("limit") int limit);
	
	void save(NoticeDto notice);

	void update(NoticeDto notice);

	void delete(int noticeNo);
	
	void updateCount(Long noticeNo);



}
