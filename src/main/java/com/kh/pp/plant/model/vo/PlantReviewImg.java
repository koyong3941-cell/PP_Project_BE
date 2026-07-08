package com.kh.pp.plant.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlantReviewImg {
	private Long imgNo;
	private Long reviewNo;
	private String originalName;
	private String saveName;
	private String imgPath;
	private Integer imgOrder;
	private Date createDate;
	private String delYn;
}
