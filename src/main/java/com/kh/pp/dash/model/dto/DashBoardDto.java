package com.kh.pp.dash.model.dto;

import java.sql.Date;

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
public class DashBoardDto {
	private Long totalMembers;
	private Long todayJoined;
	private Long todayLeaved;
	private Long leaveDate;
}
