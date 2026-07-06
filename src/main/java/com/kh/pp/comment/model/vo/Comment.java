package com.kh.pp.comment.model.vo;

import java.util.Date;

import lombok.Value;

@Value
public class Comment {
	private Long CommentNo;
	private Long memberNo;
	private Long boardNo;
	private String commentContent;
	private Date createDate;
	private String delYn;
}
