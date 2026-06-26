package com.kh.pp.member.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@Builder
public class Member {
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String email;
	private String role; 
	private Date enrollDate;
	private Date editDate;
	private String delYn;
}
