package com.kh.pp.plant.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlantReview {
	private Long reviewNo;
	private Long memberNo;
	private Long plantNo;
	private Integer rating;
	private String reviewTitle;
	private String reviewContent;
	private Date createDate;
	private String delYn;
	
}
