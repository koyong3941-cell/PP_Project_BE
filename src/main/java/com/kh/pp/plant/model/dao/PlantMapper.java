package com.kh.pp.plant.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.plant.model.dto.PlantDto;

@Mapper
public interface PlantMapper {
	
	// Read
	List<PlantDto> findPlantAll(@Param("offset") int offset, @Param("size") int size);
	
	List<PlantDto> findPlantByKeyword(
		@Param("offset") int offset
		, @Param("size") int size
		, @Param("keywordList") List<String> keywordList
		, @Param("target") String target);

	PlantDto plantDetail(Long plantNo);
	
	// Count
	int getPlantTotalElements();
	
	int getPlantTotalElementsByKeyword(
			@Param("keywordList") List<String> keywordList
			, @Param("target") String target);

	void increasePlantCount(Long plantNo);


	
}
