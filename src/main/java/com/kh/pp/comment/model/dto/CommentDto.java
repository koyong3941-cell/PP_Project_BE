package com.kh.pp.comment.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {
	private Long commentNo;
	private Long memberNo;
	private Long boardNo;
	private String commentContent;
	private Date createDate;
	private String delYn;
<<<<<<< Updated upstream
=======
	private String imgPath;
	private String saveName;
	private Integer liked;
>>>>>>> Stashed changes
}
