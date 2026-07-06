package com.kh.pp.board.model.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
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
	@NotNull(message = "카테고리를 선택해주세요.")
	private Integer categoryNo;
	private Long memberNo;
	@NotBlank(message = "제목은 공백일 수 없습니다.")
	@Size(max = 200, message = "제목은 최대 200자까지 입력 가능합니다.")
	private String boardTitle;
	@NotBlank(message = "내용은 공백일 수 없습니다.")
	@Size(max = 2000, message = "내용은 최대 2000자까지 입력 가능합니다.")
	private String boardContent;
	private Integer count;
	@Null(message = "작성일은 서버에서 자동으로 설정됩니다.")
	private Date createDate;
	private String delYn;
	
	// Join
	private String memberName;
	private String categoryName;
	private Long commentCount;
	private Long likeCount;
	private Long dislikeCount;
	
	
	// 이미지 입력용
	@Size(max = 5, message = "이미지는 최대 5장까지만 가능합니다.")
	private List<MultipartFile> imageFiles;
	
	// 이미지 출력용
	private List<BoardImgDto> boardImages;
	private String profileImage;
	
}
