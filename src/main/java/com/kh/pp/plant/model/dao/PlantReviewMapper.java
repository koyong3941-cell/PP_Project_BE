package com.kh.pp.plant.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.plant.model.dto.PlantRatingDto;
import com.kh.pp.plant.model.dto.PlantReviewDto;
import com.kh.pp.plant.model.vo.PlantReview;

@Mapper
public interface PlantReviewMapper {

	// Create
	int savePlantReview(PlantReview plantReviewEntity);
	
	// Read
	List<PlantReviewDto> findPlantReviewAll(
			@Param("offset") int offset
			, @Param("size") int size
			, @Param("plantNo") Long plantNo
			, @Param("memberNo") Long memberNo
			);

	
	PlantRatingDto getReviewRating(@Param("plantNo") Long plantNo, @Param("memberNo") Long memberNo);
	
	int hasWrittenReview(@Param("plantNo") Long plantNo, @Param("memberNo") Long memberNo);

	// Update
	int editPlantReview(PlantReview plantReviewEntity);
	
	// Count
	int getPlantReviewTotalElements(Long plantNo);
	
	// Validate
	Long getLastReviewNoByMemberNo(Long memberNo);

	int hasReviewByMemberNo(PlantReview plantReviewEntity);





}
