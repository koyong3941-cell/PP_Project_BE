package com.kh.pp.common.review;

import java.util.List;

import com.kh.pp.plant.model.dto.PlantRatingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponse<T> {
	private List<T> reviews;
    private long reviewCount;
    private PlantRatingDto ratingCount;
   
}
