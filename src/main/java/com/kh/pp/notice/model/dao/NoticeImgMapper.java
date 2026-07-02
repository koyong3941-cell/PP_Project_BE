package com.kh.pp.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.pp.notice.model.dto.NoticeImgDto;

@Mapper
public interface NoticeImgMapper {
	int insertNoticeImg(NoticeImgDto noticeImgDto);
	
	List<NoticeImgDto> findByNoticeNo(Long noticeNo);
	
}
