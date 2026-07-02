package com.kh.pp.plant.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.vo.Plant;

@Mapper
public interface PlantMapper {
	
	// Create
	void savePlant(Plant plantEntity);

	// Read
	List<PlantDto> findPlantAll(@Param("offset") int offset, @Param("limit") int limit);
	
	List<PlantDto> findPlantByKeyword(
		@Param("offset") int offset
		, @Param("limit") int limit
		, @Param("keywordList") List<String> keywordList
		, @Param("target") String target);

	PlantDto plantDetail(Long plantNo);
	
	Long getLastPlantNoByMemberNo(Long memberNo);

	// Update
	void editPlant(@Param("plant")PlantDto plant, @Param("memberNo")Long memberNo, @Param("plantNo")Long plantNo);
	
	void increasePlantCount(Long plantNo);

	// Delete
	int deletePlant(@Param("plantNo")Long plantNo, @Param("memberNo")Long memberNo);


	
}
