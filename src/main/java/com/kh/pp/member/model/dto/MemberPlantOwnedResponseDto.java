package com.kh.pp.member.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberPlantOwnedResponseDto {
	private Long memberNo;
	private Long plantNo;
	private Integer smallPlant;
	private Integer middlePlant;
	private Integer bigPlant;
}
