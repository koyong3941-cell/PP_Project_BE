package com.kh.pp.plant.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.file.service.FileService;
import com.kh.pp.plant.model.dao.PlantImgMapper;
import com.kh.pp.plant.model.dao.PlantMapper;
import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.dto.PlantImgDto;
import com.kh.pp.plant.model.vo.Plant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class PlantService {
	private final PlantMapper plantMapper;
	private final PlantImgMapper plantImgMapper;
	private final FileService fileService;

	@Transactional
	public void savePlant(@Valid PlantDto plant) {
		validatePlant(plant);
		
		Plant plantEntity = Plant.builder()
				.memberNo(plant.getMemberNo())
				.plantName(plant.getPlantName())
				.classification(plant.getClassification())
				.plantInfo(plant.getPlantInfo())
				.carbonCapture(plant.getCarbonCapture())
				.growthInfo(plant.getGrowthInfo())
				.plantApi(plant.getPlantApi())
				.build();	
		
		plantMapper.savePlant(plantEntity);
		
		Long plantNo = plantMapper.getLastPlantNoByMemberNo(plant.getMemberNo());
		
		if (plant.getImageFiles() != null) {
			long validImageCount = plant.getImageFiles().stream()
					.filter(file -> !file.isEmpty())
					.count();
			
			if (validImageCount > 5) {
				throw new FailSaveException("이미지는 최대 5장까지 업로드할 수 있습니다.");
			}
		}
		
		if (plant.getImageFiles() != null && !plant.getImageFiles().isEmpty()) {
			int order = 1;
			
			for (MultipartFile file : plant.getImageFiles()) {
				if (!file.isEmpty()) {
					try {
						String saveName = fileService.store(file, "plant");
						
						PlantImgDto imgDto = new PlantImgDto();
						imgDto.setPlantNo(plantNo);
						imgDto.setOriginalName(file.getOriginalFilename());
						imgDto.setSaveName(saveName);
						imgDto.setImgPath("/uploads/plant/");
						imgDto.setImgOrder(order++);
						
						plantImgMapper.insertPlantImg(imgDto);
						
					} catch (Exception e) {
						log.error("이미지 저장 실패", e);
						throw new FailSaveException("이미지 저장 중 오류가 발생했습니다.");
					}
				}
			}
		}
	}
	
	private void validatePlant(PlantDto plant) {
		if (plant.getPlantName() == null || plant.getPlantName().isEmpty()) {
			throw new FailSaveException("제목은 필수입니다.");
		}
		if (plant.getPlantInfo() == null || plant.getPlantInfo().isEmpty()) {
			throw new FailSaveException("내용은 필수입니다.");
		}
	}
	
	public List<PlantDto> findPlantAll(int page) {
		int offset = page * 10;
		int limit = 10;

		return plantMapper.findPlantAll(offset, limit);
	}

	public PlantDto plantDetail(Long plantNo) {
		increaseCount(plantNo);
		PlantDto plant = getPlantNoOrThrow(plantNo);

		List<PlantImgDto> images = plantImgMapper.findByPlantNo(plantNo);
		plant.setPlantImages(images);
		
		return plant;
	}
	
	private void increaseCount(Long plantNo) {
		plantMapper.increaseCount(plantNo);
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
	
	private PlantDto getPlantNoOrThrow(Long plantNo) {
		PlantDto plantDetail = plantMapper.plantDetail(plantNo);
		if (plantDetail == null) {
			throw new FailSaveException("유효하지 않은 접근입니다.");
		}
		return plantDetail; 
	}
	
}
