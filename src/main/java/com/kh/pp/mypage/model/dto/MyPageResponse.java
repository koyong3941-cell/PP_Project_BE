package com.kh.pp.mypage.model.dto;

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
public class MyPageResponse {
	private Long memberNo;
	private Long plantNo;
	private String plantName;
	private String classification;
	
	private Integer smallPlant;
	private Integer middlePlant;
	private Integer bigPlant;
	private Double smallPlantCap;
	private Double middlePlantCap;
	private Double bigPlantCap;
	private Double carbonCapture;
	private String imgPath;
	private String saveName;
	private String delYn;
}
