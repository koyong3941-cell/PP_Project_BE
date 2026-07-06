package com.kh.pp.notice.model.vo;

import java.sql.Date;

import com.kh.pp.board.model.vo.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class Notice {
	private Long noticeNo;
	private Integer memberNo;
	private String memberName;
	private String noticeTitle;
	private String noticeContent;
	private Integer noticeCount;
	private Date createDate;
	private String delYn;
}
