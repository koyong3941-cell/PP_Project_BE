package com.kh.pp.plant.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Plant {
	private Long plantNo;
	private	Integer memberNo;
	private	String plantName;
	private	String classification;
	private	Integer count;
	private	Integer carbonCapture;
	private Date createDate;
	private	String plantInfo;
	private	String growthInfo;
	private	String plantApi;
	private String delYn;
}
