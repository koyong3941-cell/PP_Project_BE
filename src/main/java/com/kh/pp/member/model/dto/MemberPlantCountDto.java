package com.kh.pp.member.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class MemberPlantCountDto {
	// 기획서 상 정규식은 ^([1-9]|[1-9][0-9]|100)$ (1~100) 이지만
	// 예시 응답 데이터에 big:0 이 존재하여 0을 허용해야 하므로 @Min(0)으로 처리함
	// (0 미허용이 맞다면 @Min(1)로 변경 필요)
	@NotNull(message = "소형 식물 개수는 필수입니다.")
	@Min(value = 0, message = "식물 개수는 0~100 사이여야 합니다.")
	@Max(value = 100, message = "식물 개수는 최대 100개까지 등록 가능합니다.")
	private Integer small;

	@NotNull(message = "중형 식물 개수는 필수입니다.")
	@Min(value = 0, message = "식물 개수는 0~100 사이여야 합니다.")
	@Max(value = 100, message = "식물 개수는 최대 100개까지 등록 가능합니다.")
	private Integer middle;

	@NotNull(message = "대형 식물 개수는 필수입니다.")
	@Min(value = 0, message = "식물 개수는 0~100 사이여야 합니다.")
	@Max(value = 100, message = "식물 개수는 최대 100개까지 등록 가능합니다.")
	private Integer big;
}