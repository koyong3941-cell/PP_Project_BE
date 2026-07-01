package com.kh.pp.board.model.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
	private Long memberNo;
	@NotBlank(message = "공백일 수 없습니다.")
	private String boardTitle;
	@NotBlank(message = "공백일 수 없습니다.")
	private String boardContent;
	private Integer count;
	private Date createDate;
	private String delYn;
	
	// Join
	private String memberName;
	private String categoryName;
	private Long commentCount;
	private Long likeCount;
	private Long dislikeCount;
	
	// 이미지 입력용
	private List<MultipartFile> imageFiles;
	
	// 이미지 출력용
	private List<BoardImgDto> boardImages;
	
}
