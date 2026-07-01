package com.kh.pp.plant.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.pp.plant.model.dto.PlantImgDto;

@Mapper
public interface PlantImgMapper {
	int insertPlantImg(PlantImgDto plantImgDto);
	
	List<PlantImgDto> findByPlantNo(Long plantNo);
}
