package com.kh.pp.member.model.vo;

import lombok.Value;

@Value
public class MemberPlant {
	private Long memberNo;
	private Long plantNo;
	private Integer smallPlant;
	private Integer middlePlant;
	private Integer bigPlant;
}
