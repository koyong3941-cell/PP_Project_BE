package com.kh.pp.notice.model.dao;

import java.util.List;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.notice.model.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
	@Select("SELECT * FROM NOTICE WHERE DEL_YN = 'N' ORDER BY NOTICE_NO DESC")
	List<NoticeDto> findAll();

	@Select("SELECT * FROM NOTICE WHERE NOTICE_NO = #{noticeNo}")
	NoticeDto findById(int noticeNo);
	
	@Insert("INSERT INTO NOTICE VALUES (NOTICE_SEQ.NEXTVAL, #{memberNo},#{noticeTitle},#{noticeContent},0,SYSDATE,'N')")
	void saveNotice(NoticeDto notice);

	@Update("UPDATE NOTICE SET NOTICE_TITLE = #{noticeTitle},NOTICE_CONTENT = #{noticeContent} WHERE NOTICE_NO = #{noticeNo}")
	void editNotice(NoticeDto notice);

	@Update("UPDATE NOTICE SET DEL_YN = 'Y' WHERE NOTICE_NO = #{noticeNo}")
	void delete(int noticeNo);
	
	@Update("UPDATE NOTICE SET NOTICE_COUNT = NOTICE_COUNT + 1 WHERE NOTICE_NO = #{noticeNo}")
	void updateCount(int noticeNo);




}
