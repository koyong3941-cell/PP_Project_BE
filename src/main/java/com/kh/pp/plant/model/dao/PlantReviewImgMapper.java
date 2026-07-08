package com.kh.pp.plant.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.plant.model.dto.PlantReviewImgDto;

@Mapper
public interface PlantReviewImgMapper {

	int insertPlantReviewImg(PlantReviewImgDto imgDto);

	List<PlantReviewImgDto> findImagesByReviewNos(@Param("reviewNos") List<Long> reviewNos);
	
	void deletePlantReviewImgByReviewNo(Long plantNo);

}
