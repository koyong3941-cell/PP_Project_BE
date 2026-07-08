package com.kh.pp.plant.model.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.DuplicateMemberException;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.FailUpdateException;
import com.kh.pp.exception.PlantNotFoundException;
import com.kh.pp.file.service.FileService;
import com.kh.pp.plant.model.dao.PlantMapper;
import com.kh.pp.plant.model.dao.PlantReviewImgMapper;
import com.kh.pp.plant.model.dao.PlantReviewMapper;
import com.kh.pp.plant.model.dto.PlantRatingDto;
import com.kh.pp.plant.model.dto.PlantReviewDto;
import com.kh.pp.plant.model.dto.PlantReviewImgDto;
import com.kh.pp.plant.model.vo.PlantReview;

import jakarta.validation.Valid;
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

	
	// Create
	@Transactional
	public void savePlantReview(PlantReviewDto plantReview) {
		long count = validatePlantReviewImages(plantReview.getImageFiles());
		
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
	
	// Read
	public PageResponse<PlantReviewDto> findPlantReviewAll(int page, Long plantNo, Long memberNo) {
		int size = 10;
		int offset = page * size;
		
		int totalElements = plantReviewMapper.getPlantReviewTotalElements(plantNo);
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<PlantReviewDto> plantReviews = plantReviewMapper.findPlantReviewAll(offset, size, plantNo, memberNo);
		
		// 출력할 리뷰 번호들 추출(이미지 출력을 위해서)
		List<Long> reviewNos = plantReviews.stream()
	            .map(PlantReviewDto::getReviewNo)
	            .collect(Collectors.toList());
		
		List<PlantReviewImgDto> images = plantReviewImgMapper.findImagesByReviewNos(reviewNos);
		
		Map<Long, List<PlantReviewImgDto>> imageMap = images.stream()
				.collect(Collectors.groupingBy(PlantReviewImgDto::getReviewNo));
		
		for (PlantReviewDto plantReview : plantReviews) {
		    List<PlantReviewImgDto> reviewImages = imageMap.getOrDefault(plantReview.getReviewNo(), Collections.emptyList());
		    plantReview.setPlantReviewImages(reviewImages);
		}
		
		return new PageResponse<>(plantReviews, totalElements, page, size);
	}
	
	public PlantRatingDto getReviewRating(Long plantNo, Long memberNo) {
		
		PlantRatingDto rating = plantReviewMapper.getReviewRating(plantNo, memberNo);
		
		boolean hasMyReview = false;
		if(memberNo != null) {
			int count = plantReviewMapper.hasWrittenReview(plantNo, memberNo);
			hasMyReview = count > 0;
		}
		rating.setHasMyReview(hasMyReview);
		
		return rating;
	}
	
	@Transactional
	// Update
	public void editPlantReview(PlantReviewDto plantReview, Long memberNo, Long reviewNo) {
		long count = validatePlantReviewImages(plantReview.getImageFiles());
		
		PlantReview plantReviewEntity = PlantReview.builder()
				.reviewNo(reviewNo)
				.memberNo(memberNo)
				.plantNo(plantReview.getPlantNo())
				.reviewTitle(plantReview.getReviewTitle())
				.reviewContent(plantReview.getReviewContent())
				.rating(plantReview.getRating())
				.build();
		
		Integer result = plantReviewMapper.editPlantReview(plantReviewEntity);
		
		if (result == null || result == 0) {
			throw new FailUpdateException("리뷰 수정에 실패했습니다.");
		}
		
		plantReviewImgMapper.deletePlantReviewImgByReviewNo(plantReviewEntity.getReviewNo());
		
		if(count > 0) {
			savePlantReviewImg(reviewNo, plantReview.getImageFiles());
		}
	}
	
	// Delete
	@Transactional
	public void deletePlantReview(Long memberNo, Long reviewNo) {
		Integer result = plantReviewMapper.deletePlantReview( memberNo, reviewNo);
		
		if (result == 0 || result == null) {
			throw new FailDeleteException("리뷰 삭제에 실패하였습니다.");
		}
	}
	
	
	
	// ------ 식물 게시글 활성 여부 확인 ------
	private void isActivePlant(Long plantNo) {
		int result = plantMapper.isActivePlant(plantNo);
		
		if (result < 1 ) {
			throw new PlantNotFoundException("해당 식물을 찾을 수 없습니다.");
		}
	}
	
	// ------ 식물 리뷰 이미지 갯수 확인 ------
	private long validatePlantReviewImages(List<MultipartFile> imageFiles) {
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
