package com.kh.pp.plant.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlantImg {
	private Long imgNo;
	private Long plantNo;
	private String originalName;
	private String saveName;
	private String imgPath;
	private Integer imgOrder;
	private Date createDate;
	private String delYn;
}
