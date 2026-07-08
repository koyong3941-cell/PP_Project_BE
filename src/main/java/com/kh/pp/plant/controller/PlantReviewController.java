package com.kh.pp.plant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.plant.model.dto.PlantReviewDto;
import com.kh.pp.plant.model.service.PlantReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plants/{plantNo}/reviews")
public class PlantReviewController {
	private final PlantReviewService plantReviewService; 
 

	// Create
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> savePlantReview (
				@AuthenticationPrincipal CustomUserDetails userDetails
				, @ModelAttribute @Valid PlantReviewDto plantReview
				, @PathVariable(name = "plantNo") Long plantNo
				){
		plantReview.setMemberNo(userDetails.getMemberNo());
		plantReview.setPlantNo(plantNo);
		plantReviewService.savePlantReview(plantReview);
		
		return ResponseEntity.status(201).body(ApiResponse.created(null));
	}
}
