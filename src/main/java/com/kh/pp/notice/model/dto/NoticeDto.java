package com.kh.pp.notice.model.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeDto {
	
	private Long noticeNo;
	private Integer memberNo;
	@NotBlank
	private String noticeTitle;
	@NotBlank
	private String noticeContent;
	private Integer noticeCount;
	private Date createDate;
	private String delYn;
	   
	
}
