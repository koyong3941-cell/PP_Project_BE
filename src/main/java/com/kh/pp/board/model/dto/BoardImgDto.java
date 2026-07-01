package com.kh.pp.board.model.dto;

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
public class BoardImgDto {
	private Long boardImgNo;
	private Long boardNo;
	private String originalName;
	private String saveName;
	private String imgPath;
	private Integer imgOrder;
	private Date createDate;
	private String delYn;
}