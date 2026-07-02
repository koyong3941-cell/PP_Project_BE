package com.kh.pp.notice.model.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class NoticeImgDto {

	private Long noticeImgNo;
	private Long noticeNo;
	private String originalName;
	private String savename;
	private String imgPath;
	private Integer imgOrder;
	private Date createDate;
	private String delYn;
	
}
