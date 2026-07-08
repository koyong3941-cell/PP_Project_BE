package com.kh.pp.notice.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@Builder
public class Notice {
	
	private Long noticeNo; 
	private Long memberNo;
	private String noticeTitle;
	private String noticeContent;
	private Integer noticeCount;
	private Date createDate;
	private String delYn;
}
