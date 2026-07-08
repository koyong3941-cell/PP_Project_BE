package com.kh.pp.member.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class MemberPlantRequestDto {
	@NotNull(message = "식물 개수 정보는 필수입니다.")
	@Valid
	private MemberPlantCountDto memberPlantCount;
}