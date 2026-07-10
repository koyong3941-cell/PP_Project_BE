package com.kh.pp.mypage.model.dto;

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
public class MyPagePlantDetail {
	private Long memberNo;
	private Long plantNo;
	private String plantName;
	private String classification;
	private Double rating;
	private Integer smallPlant;
	private Integer middlePlant;
	private Integer bigPlant;
}
