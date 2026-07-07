package com.kh.pp.plant.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.pp.plant.model.vo.PlantReview;

@Mapper
public interface PlantReviewMapper {

	// Create
	int savePlantReview(PlantReview plantReviewEntity);
	
	// Validate
	Long getLastReviewNoByMemberNo(Long memberNo);

	int hasReviewByMemberNo(PlantReview plantReviewEntity);

}
