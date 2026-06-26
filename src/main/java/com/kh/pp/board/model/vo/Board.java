package com.kh.pp.board.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class Board {
	private Long boardNo;
	private Integer categoryNo;
	private Integer memberNo;
	private String boardTitle;
	private String boardContent;
	private Integer count;
	private Date createDate;
	private String delYn;
}
