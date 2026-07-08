package com.kh.pp.plant.model.dto;

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
public class PlantRatingDto {
	private Integer totalRating;
	private Double averageRating;
	private Integer one;
	private Integer two;
	private Integer three;
	private Integer four;
	private Integer five;
	
	private boolean hasMyReview;
}
