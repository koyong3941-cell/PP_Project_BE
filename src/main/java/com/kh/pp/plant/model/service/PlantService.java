package com.kh.pp.plant.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.plant.model.dao.PlantMapper;
import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.vo.Plant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class PlantService {
	private final PlantMapper plantMapper;

	public List<PlantDto> findPlantAll(int page) {
		int offset = page * 10;
		int limit = 10;

		return plantMapper.findPlantAll(offset, limit);
	}

	public PlantDto plantDetail(Long plantNo) {
		increaseCount(plantNo);
		PlantDto plant = getPlantNoOrThrow(plantNo);

		return plant;
	}
	
	private void increaseCount(Long plantNo) {
		plantMapper.increaseCount(plantNo);
		
	}
	
	private PlantDto getPlantNoOrThrow(Long plantNo) {
		PlantDto plantDetail = plantMapper.plantDetail(plantNo);
		if (plantDetail == null) {
			throw new FailSaveException("유효하지 않은 접근입니다.");
		}
		return plantDetail; 
	}

	@Transactional
	public void savePlant(@Valid PlantDto plant) {
		validatePlant(plant);
		Plant plantEntity = Plant.builder()
				.memberNo(plant.getMemberNo())
				.plantName(plant.getPlantName())
				.plantInfo(plant.getPlantInfo())
				.build();	
		
		plantMapper.savePlant(plantEntity);
		
	}
	
	private void validatePlant(PlantDto plant) {
		if (plant.getPlantName() == null || plant.getPlantName().isEmpty()) {
			throw new FailSaveException("제목은 필수입니다.");
		}
		if (plant.getPlantInfo() == null || plant.getPlantInfo().isEmpty()) {
			throw new FailSaveException("내용은 필수입니다.");
		}
	}

	@Transactional
	public void deletePlant(Long plantNo, int memberNo) {
	int result = plantMapper.deletePlant(plantNo, memberNo);
		
		if (result < 1) {
			throw new FailDeleteException("삭제에 실패하였습니다.");
		}
	}
	
	@Transactional
	public void editPlant(PlantDto plant, int memberNo, Long plantNo) {
		plantMapper.editPlant(plant, memberNo, plantNo);
	}
	
	
}
