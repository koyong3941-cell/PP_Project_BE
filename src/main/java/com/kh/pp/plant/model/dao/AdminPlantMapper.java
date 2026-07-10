package com.kh.pp.plant.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.vo.Plant;

@Mapper
public interface AdminPlantMapper {
	
	// Create
	int savePlant(Plant plantEntity);
	
	// Read
	List<PlantDto> findPlantAll(@Param("offset") int offset, @Param("size") int size);
	
	List<PlantDto> findPlantByKeyword(
			@Param("offset") int offset
			, @Param("size") int size
			, @Param("keywordList") List<String> keywordList
			, @Param("target") String target);
		
	PlantDto plantDetail(Long plantNo);
	
	// Update
	int editPlant(Plant plant);
	
	int restorePlants(@Param("plantNos") List<Long> plantNos);

	// Delete
	int deletePlants(@Param("plantNos") List<Long> plantNos);
	
	// Count
	int getPlantTotalElements();
	
	int getPlantTotalElementsByKeyword(
			@Param("keywordList") List<String> keywordList
			, @Param("target") String target);
	
	// Validate
	Long getLastPlantNoByMemberNo(Long memberNo);

		
}
