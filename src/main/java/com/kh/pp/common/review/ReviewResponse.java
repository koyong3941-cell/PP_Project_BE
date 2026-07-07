package com.kh.pp.common.review;

import com.kh.pp.common.page.PageResponse;
import com.kh.pp.plant.model.dto.PlantRatingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponse<T> {
	private PageResponse<T> reviews;
    private long reviewCount;
    private PlantRatingDto ratingCount;
    
    public static <T> ReviewResponse<T> empty(int page, int size){
    	return new ReviewResponse<>(PageResponse.empty(page, size), 0, null);
    }
   
}
