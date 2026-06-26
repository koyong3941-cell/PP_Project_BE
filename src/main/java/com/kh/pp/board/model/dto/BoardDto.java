package com.kh.pp.board.model.dto;

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
public class BoardDto {
	private Long boardNo;
	private Integer categoryNo;
	private Integer memberNo;
	@NotBlank
	private String boardTitle;
	@NotBlank
	private String boardContent;
	private Integer count;
	private Date createDate;
	private String delYn;
	
	// Join
	private String memberName;
	private String categoryName;
}
