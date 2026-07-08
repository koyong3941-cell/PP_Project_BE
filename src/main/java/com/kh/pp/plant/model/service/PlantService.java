package com.kh.pp.plant.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.FailUpdateException;
import com.kh.pp.exception.PlantNotFoundException;
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
		
		int result = plantMapper.savePlant(plantEntity);
		
		if(result < 1) {
			throw new FailSaveException("작성에 실패했습니다.");
		}
		if(count > 0) {
			Long plantNo = plantMapper.getLastPlantNoByMemberNo(plantEntity.getMemberNo());

			savePlantImg(plantNo, plant.getImageFiles());
		}
		
	}
	
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
	
	// Update
	@Transactional
	public void editPlant(PlantDto plant) {
		long count = validatePlantImages(plant.getImageFiles());
		
		Plant plantEntity = Plant.builder()
				.plantNo(plant.getPlantNo())
				.memberNo(plant.getMemberNo())
				.plantName(plant.getPlantName())
				.classification(plant.getClassification())
				.plantInfo(plant.getPlantInfo())
				.carbonCapture(plant.getCarbonCapture())
				.growthInfo(plant.getGrowthInfo())
				.plantApi(plant.getPlantApi())
				.build();
		
		int result = plantMapper.editPlant(plantEntity);
		
		if (result < 1) {
			throw new FailUpdateException("수정에 실패했습니다.");
		}
		
		plantImgMapper.deletePlantImgByPlantNo(plant.getPlantNo());
		
		if(count > 0) {
			savePlantImg(plant.getPlantNo(), plant.getImageFiles());
		}
	}

	private void increasePlantCount(Long plantNo) {
		plantMapper.increasePlantCount(plantNo);
	}

	// Delete
	@Transactional
	public void deletePlant(Long plantNo, Long memberNo) {
	int result = plantMapper.deletePlant(plantNo);
		
		if (result < 1) {
			throw new FailDeleteException("삭제에 실패하였습니다.");
		}
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
	private void savePlantImg(Long plantNo, List<MultipartFile> imageFiles) {
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
