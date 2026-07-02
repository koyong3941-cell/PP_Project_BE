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

	// Create
	@Transactional
	public void savePlant(PlantDto plant) {
		long count = validatePlantImages(plant.getImageFiles());
		
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
		
		if(count > 0) {
			Long plantNo = plantMapper.getLastPlantNoByMemberNo(plant.getMemberNo());

			saveBoardImage(plantNo, plant.getImageFiles());
		}
		
	}
	
	// Read
	public List<PlantDto> findPlantAll(int page) {
		int offset = page * 10;
		int limit = 10;

		return plantMapper.findPlantAll(offset, limit);
	}

	public PlantDto plantDetail(Long plantNo) {
		increasePlantCount(plantNo);
		PlantDto plant = getPlantNoOrThrow(plantNo);

		List<PlantImgDto> images = plantImgMapper.findByPlantNo(plantNo);
		plant.setPlantImages(images);
		
		return plant;
	}
	
	// Update
	@Transactional
	public void editPlant(PlantDto plant, Long memberNo, Long plantNo) {
		plantMapper.editPlant(plant, memberNo, plantNo);
	}

	private void increasePlantCount(Long plantNo) {
		plantMapper.increasePlantCount(plantNo);
	}

	// Delete
	@Transactional
	public void deletePlant(Long plantNo, Long memberNo) {
	int result = plantMapper.deletePlant(plantNo, memberNo);
		
		if (result < 1) {
			throw new FailDeleteException("삭제에 실패하였습니다.");
		}
	}
	
	// ------ 접근 실패 시 ------
	private PlantDto getPlantNoOrThrow(Long plantNo) {
		PlantDto plantDetail = plantMapper.plantDetail(plantNo);
		if (plantDetail == null) {
			throw new FailSaveException("유효하지 않은 접근입니다.");
		}
		return plantDetail; 
	}
	
	// ------ 식물 이미지 갯수 확인 ------
	private long validatePlantImages(List<MultipartFile> imageFiles) {
		if (imageFiles == null) {
			return 0;
		}
		
		long count = imageFiles.stream()
							   .filter(file -> !file.isEmpty())
							   .count();
			
		if (count > 5) {
			throw new FailSaveException("이미지는 최대 5장까지 업로드할 수 있습니다.");
		}
		
		return count;
	}
	
	// ------ 식물 이미지 저장 ------
	private void saveBoardImage(Long plantNo, List<MultipartFile> imageFiles) {
		if (imageFiles == null || imageFiles.isEmpty()) {
			return;
		}
	    int order = 1;

        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                try {
                    String saveName = fileService.store(file, "plant");

                    PlantImgDto imgDto = new PlantImgDto();
                    imgDto.setPlantNo(plantNo);
                    imgDto.setOriginalName(file.getOriginalFilename());
                    imgDto.setSaveName(saveName);
                    imgDto.setImgPath("/uploads/plant/");
                    imgDto.setImgOrder(order++);

                    int imgResult = plantImgMapper.insertPlantImg(imgDto);
	                    
                    if (imgResult < 1) {
                    	throw new FailSaveException("이미지 저장에 실패했습니다.");
                    }
                } catch (Exception e) {
                    log.error("이미지 저장 실패", e);
                    throw new FailSaveException("이미지 저장 중 오류가 발생했습니다.");
                }
            }
        }
	}
}
