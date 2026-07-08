package com.kh.pp.plant.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.pp.plant.model.vo.Plant;

@Mapper
public interface AdminPlantMapper {
	
	// Create
	int savePlant(Plant plantEntity);
		
	// Update
	int editPlant(Plant plant);
	
	void increasePlantCount(Long plantNo);
	
	// Delete
	int deletePlant(Long plantNo);
	
	// Validate
	Long getLastPlantNoByMemberNo(Long memberNo);
		
}
