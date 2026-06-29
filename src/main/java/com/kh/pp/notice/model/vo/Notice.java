package com.kh.pp.notice.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@Builder
public class Notice {
	private int noticeNo;
	private int memberNo;
	private String noticeTitle;
	private String noticeContent;
	private int noticeCount;
	private Date createDate;
	private String delYn;
}
