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
	
	Integer addPlantReviewLike(@Param("memberNo") Long memberNo, @Param("reviewNo") Long reviewNo);
	
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
	Integer editPlantReview(PlantReview plantReviewEntity);
	
	// Delete
	Integer deletePlantReview(@Param("memberNo") Long memberNo, @Param("reviewNo") Long reviewNo);
	
	Integer deletePlantReviewLike(@Param("memberNo") Long memberNo, @Param("reviewNo") Long reviewNo);

	// Count
	int getPlantReviewTotalElements(Long plantNo);
	
	// Validate
	int isActivePlant(Long plantNo);
	
	Long getLastReviewNoByMemberNo(Long memberNo);

	int hasReviewByMemberNo(PlantReview plantReviewEntity);
	
	int isActivePlantReviewLike(@Param("memberNo") Long memberNo, @Param("reviewNo") Long reviewNo);



}
