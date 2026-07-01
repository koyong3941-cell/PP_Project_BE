package com.kh.pp.plant.model.dto;

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
public class PlantDto {
	private Long plantNo;
	private	Integer memberNo;
	@NotBlank
	private	String plantName;
	private	String classification;
	private	Integer count;
	private	Integer carbonCapture;
	private Date createDate;
	@NotBlank
	private	String plantInfo;
	private	String growthInfo;
	private	String plantApi;
	private String delYn;
	
	// 이미지 입력용
	private List<MultipartFile> imageFiles;
		
	// 이미지 출력용
	private List<PlantImgDto> plantImages;
}
