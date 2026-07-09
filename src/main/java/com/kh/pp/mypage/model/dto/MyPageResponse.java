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
	
	private Double smallPlant;
	private Double middlePlant;
	private Double bigPlant;
	private Double smallPlantCap;
	private Double middlePlantCap;
	private Double bigPlantCap;
	private Double carbonCapture;
}
