package com.kh.pp.board.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Category {
	private int categoryNo;
	private String categoryName;

}
