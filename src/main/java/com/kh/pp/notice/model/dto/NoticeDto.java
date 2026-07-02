package com.kh.pp.notice.model.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeDto {

	private int noticeNo;
	private int memberNo;
	private String noticeTitle;
	private String noticeContent;
	private int noticeCount;
	private Date createDate;
	private String delYn;
	
	// join
	private String memberName;
	private List<MultipartFile> imageFiles;
	private List<NoticeImgDto> noticeImages; 

	
	
}
