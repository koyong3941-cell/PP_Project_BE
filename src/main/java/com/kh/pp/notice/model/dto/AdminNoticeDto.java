package com.kh.pp.notice.model.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminNoticeDto {
	private Long noticeNo;
	private Long memberNo;
	@NotBlank
	private String noticeTitle;
	private String noticeContent;
	private Integer noticeCount;
	private Date createDate;
	private String delYn;
	
	// join
	private String memberName;
	private String memberId;
	
	//이미지 입력용
	private List<MultipartFile> imageFiles;
	
	//이미지 출력용
	private List<NoticeImgDto> noticeImages; 
	private String profileImage;
}
