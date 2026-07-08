package com.kh.pp.plant.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.pp.plant.model.dto.PlantReviewImgDto;

@Mapper
public interface PlantReviewImgMapper {

	int insertPlantReviewImg(PlantReviewImgDto imgDto);

}
