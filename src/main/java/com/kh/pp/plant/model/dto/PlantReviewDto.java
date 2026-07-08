package com.kh.pp.plant.model.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class PlantReviewDto {
	
	private Long reviewNo;
	private Long memberNo;
	private Long plantNo;
	@NotNull(message = "평점은 필수입니다.")
	@Min(value = 1, message = "평점은 최소 1점 이상이어야 합니다.")
	@Max(value = 10, message = "평점은 최대 10점까지입니다.")
	private Integer rating;
	@NotBlank(message = "리뷰제목은 공백일 수 없습니다.")
	@Size(max = 100, message = "리뷰제목은 최대 100자까지 입력 가능합니다.")
	private String reviewTitle;
	@NotBlank(message = "리뷰내용은 공백일 수 없습니다.")
	@Size(max = 500, message = "리뷰내용은 최대 500자까지 입력 가능합니다.")
	private String reviewContent;
	@Null(message = "작성일은 서버에서 자동으로 설정됩니다.")	
	private Date createDate;
	private String delYn;

	// Join
	private String memberName;
	private Integer likeCount;
	
	// 사용자가 좋아요 했는지 확인용
	private boolean isLiked;
	
	// 이미지 입력용
	@Size(max = 5, message = "이미지는 최대 5장까지만 가능합니다.")
	private List<MultipartFile> imageFiles;

	// 이미지 출력용
	private List<PlantReviewImgDto> plantReviewImages;
}
