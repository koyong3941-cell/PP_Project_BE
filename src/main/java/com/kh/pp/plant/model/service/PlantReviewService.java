package com.kh.pp.plant.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.exception.DuplicateMemberException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.PlantNotFoundException;
import com.kh.pp.file.service.FileService;
import com.kh.pp.plant.model.dao.PlantMapper;
import com.kh.pp.plant.model.dao.PlantReviewImgMapper;
import com.kh.pp.plant.model.dao.PlantReviewMapper;
import com.kh.pp.plant.model.dto.PlantReviewDto;
import com.kh.pp.plant.model.dto.PlantReviewImgDto;
import com.kh.pp.plant.model.vo.PlantReview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class PlantReviewService {
	private final PlantMapper plantMapper;
	private final PlantReviewMapper plantReviewMapper;
	private final PlantReviewImgMapper plantReviewImgMapper;
	private final FileService fileService;

	@Transactional
	public void savePlantReview(PlantReviewDto plantReview) {
		long count = validatePlantImages(plantReview.getImageFiles());
		
		PlantReview plantReviewEntity = PlantReview.builder()
				.memberNo(plantReview.getMemberNo())
				.plantNo(plantReview.getPlantNo())
				.reviewTitle(plantReview.getReviewTitle())
				.reviewContent(plantReview.getReviewContent())
				.rating(plantReview.getRating())
				.build();
		
		isActivePlant(plantReviewEntity.getPlantNo());

		// 같은 식물게시글에 리뷰 두개 이상 남기지 못하게 체크
		int hasReview = plantReviewMapper.hasReviewByMemberNo(plantReviewEntity);
		if(hasReview > 0) {
			throw new DuplicateMemberException("이미 작성한 리뷰가 있습니다.");
		}
		
		int result = plantReviewMapper.savePlantReview(plantReviewEntity);
		if(result < 1) {
			throw new FailSaveException("작성에 실패했습니다.");
		}
		
		if(count > 0) {
			Long reviewNo = plantReviewMapper.getLastReviewNoByMemberNo(plantReview.getMemberNo());
			
			savePlantReviewImg(reviewNo, plantReview.getImageFiles());
		}
	}
	
	
	
	// ------ 식물 게시글 활성 여부 확인 ------
	private void isActivePlant(Long plantNo) {
		int result = plantMapper.isActivePlant(plantNo);
		
		if (result < 1 ) {
			throw new PlantNotFoundException("해당 식물 게시글이 존재하지 않습니다.");
		}
	}
	
	// ------ 식물 리뷰 이미지 갯수 확인 ------
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
	
	// ------ 식물 리뷰 이미지 저장
	private void savePlantReviewImg(Long reviewNo, List<MultipartFile> imageFiles) {
		if (imageFiles == null || imageFiles.isEmpty()) {
			return;
		}
	    int order = 1;

        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                try {
                    String saveName = fileService.store(file, "plantReview");

                    PlantReviewImgDto imgDto = new PlantReviewImgDto();
                    imgDto.setReviewNo(reviewNo);
                    imgDto.setOriginalName(file.getOriginalFilename());
                    imgDto.setSaveName(saveName);
                    imgDto.setImgPath("/uploads/plantReview/");
                    imgDto.setImgOrder(order++);

                    int imgResult = plantReviewImgMapper.insertPlantReviewImg(imgDto);
	                    
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
