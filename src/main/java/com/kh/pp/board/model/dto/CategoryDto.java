package com.kh.pp.board.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CategoryDto {

	private int categoryNo;
	@NotNull (message = "카테고리 이름은 비어 있을 수 없습니다.")
	@Size(max = 20, message = "카테고리 이름은 최대 20자까지 가능합니다.")
	private String categoryName;
}
