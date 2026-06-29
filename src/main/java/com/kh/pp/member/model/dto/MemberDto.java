package com.kh.pp.member.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MemberDto {
	private int memberNo;
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String email;
	private String role; 
	private Date enrollDate;
	private Date editDate;
	private String delYn;
}
