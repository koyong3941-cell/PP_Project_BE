package com.kh.pp.plant.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.plant.model.dao.PlantImgMapper;
import com.kh.pp.plant.model.dao.PlantMapper;
import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.dto.PlantImgDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class PlantService {
	private final PlantMapper plantMapper;
	private final PlantImgMapper plantImgMapper;

	// Read
	public PageResponse<PlantDto> findPlantAll(int page, int size) {
		int offset = page * size;

		int totalElements = plantMapper.getPlantTotalElements();
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<PlantDto> plants = plantMapper.findPlantAll(offset, size);
		
		return new PageResponse<>(plants, totalElements, page, size);
	}
	
	public PageResponse<PlantDto> findPlantByKeyword(int page, int size, String keyword, String target) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return findPlantAll(page, size);
		}
		
		int offset = page * size;
		
		List<String> keywordList = new ArrayList<>();
		String[] words = keyword.trim().split("\\s+");
		for (String word : words) {
			if (!word.isEmpty()) {
				keywordList.add(word);
			}
		}
		
		if (target == null || target.trim().isEmpty()) {
			target = "all";
		}
		
		int totalElements = plantMapper.getPlantTotalElementsByKeyword(keywordList, target);
		
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<PlantDto> plants = plantMapper.findPlantByKeyword(offset, size, keywordList, target);
	
		return new PageResponse<>(plants, totalElements, page, size);
	}

	public PlantDto plantDetail(Long plantNo) {
		PlantDto plant = plantMapper.plantDetail(plantNo);
		if (plant == null) {
			throw new FailSaveException("해당 식물이 존재하지 않습니다.");
		}
		List<PlantImgDto> images = plantImgMapper.findPlantImgByPlantNo(plantNo);
		plant.setPlantImages(images);
		increasePlantCount(plantNo);
		
		return plant;
	}
	
	// Count
	private void increasePlantCount(Long plantNo) {
		plantMapper.increasePlantCount(plantNo);
	}



}
