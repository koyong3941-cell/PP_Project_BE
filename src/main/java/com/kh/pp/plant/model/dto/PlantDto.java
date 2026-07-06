package com.kh.pp.plant.model.dto;

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
public class PlantDto {
	
	private Long plantNo;
	private	Long memberNo;
	@NotBlank(message = "식물 이름은 공백일 수 없습니다.")
	@Size(max = 20, message = "식물 이름은 최대 20자까지 입력 가능합니다.")
	private	String plantName;
	@NotBlank(message = "식물종은 공백일 수 없습니다.")
	@Size(max = 20, message = "식물종은 최대 20자까지 입력 가능합니다.")
	private	String classification;
	private	Integer count;
	@NotNull(message = "탄소포집량은 공백일 수 없습니다.")
	private	Integer carbonCapture;
	@Null(message = "작성일은 서버에서 자동으로 설정됩니다.")
	private Date createDate;
	@NotBlank(message = "식물정보는 공백일 수 없습니다.")
	@Size(max = 2000, message = "식물정보는 최대 2000자까지 입력 가능합니다.")
	private	String plantInfo;
	@NotBlank(message = "재배정보는 공백일 수 없습니다.")
	@Size(max = 2000, message = "재배정보는 최대 2000자까지 입력 가능합니다.")
	private	String growthInfo;
	@Size(max = 300, message = "API 주소는 최대 300자까지 입력 가능합니다.")
	private	String plantApi;
	private String delYn;
	
	// Join
	private String memberName;
	private Integer reviewCount;
	private Integer avgRating;
	
	// 이미지 입력용
	@Size(max = 5, message = "이미지는 최대 5장까지만 가능합니다.")
	private List<MultipartFile> imageFiles;
		
	// 이미지 출력용
	private List<PlantImgDto> plantImages;
	private String profileImage;
	private String mainPlantImage;
}
