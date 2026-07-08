package com.kh.pp.comment.model.vo;

import java.util.Date;

import lombok.Value;

@Value
public class Comment {
	private Long CommentNo;
	private Long memberNo;
	private Long boardNo;
	private String memberName;
	private String commentContent;
	private Date createDate;
	private String delYn;
	private String imgPath;
	private String saveName;
}
