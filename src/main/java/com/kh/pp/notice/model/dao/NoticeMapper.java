package com.kh.pp.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {
	List<NoticeDto> findNoticeAll(@Param("offset") int offset, @Param("limit") int limit);

	NoticeDto findById(Long noticeNo);
	
	List<NoticeDto> search(@Param("keyword")String keyword,@Param("offset")int offset,@Param("limit") int limit);
	
	void save(Notice notice);

	void update(@Param("notice")NoticeDto notice,@Param("noticeNo")Long noticeNo,@Param("file") MultipartFile file);

	void delete(@Param("noticeNo")Long noticeNo);
	
	void updateCount(Long noticeNo);

	Long getLastBoardNoByMemberNo(int memberNo);



}
