package com.kh.pp.plant.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.vo.Plant;

@Mapper
public interface PlantMapper {
	
	List<PlantDto> findPlantAll(@Param("offset") int offset, @Param("limit") int limit);
	
	void increasePlantCount(Long plantNo);
	
	PlantDto plantDetail(Long plantNo);
	
	int deletePlant(@Param("plantNo")Long plantNo, @Param("memberNo")Long memberNo);
	
	void savePlant(Plant plantEntity);

	void editPlant(@Param("plant")PlantDto plant, @Param("memberNo")Long memberNo, @Param("plantNo")Long plantNo);

	Long getLastPlantNoByMemberNo(Long memberNo);
	
}
