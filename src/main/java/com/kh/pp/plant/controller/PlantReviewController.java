package com.kh.pp.plant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.plant.model.dto.PlantRatingDto;
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
	
	@PostMapping("/{reviewNo}/like")
	public ResponseEntity<ApiResponse<Void>> addPlantReviewLike (
			@AuthenticationPrincipal CustomUserDetails userDetails
			, @PathVariable(name = "reviewNo") Long reviewNo
			){
		Long memberNo = userDetails.getMemberNo();
		plantReviewService.addPlantReviewLike(memberNo, reviewNo);
		return ResponseEntity.status(201).body(ApiResponse.created(null));
	}
	
	// Read
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<PlantReviewDto>>> findPlantReviewAll(
			@RequestParam(name = "page", defaultValue = "0") int page
			, @PathVariable(name = "plantNo") Long plantNo
			, @AuthenticationPrincipal CustomUserDetails userDetails
			){
		Long memberNo = (userDetails != null) ? userDetails.getMemberNo() : null;
		
		PageResponse<PlantReviewDto> plantReviews = plantReviewService.findPlantReviewAll(page, plantNo, memberNo);
		
		return ResponseEntity.status(200).body(ApiResponse.success(plantReviews));
	}
	
	@GetMapping("/rating")
	public ResponseEntity<ApiResponse<PlantRatingDto>> getReviewRating(
			@PathVariable(name = "plantNo") Long plantNo
			, @AuthenticationPrincipal CustomUserDetails userDetails
			){
		Long memberNo = (userDetails != null) ? userDetails.getMemberNo() : null;

		PlantRatingDto rating = plantReviewService.getReviewRating(plantNo, memberNo);
		
		return ResponseEntity.status(200).body(ApiResponse.success(rating));
	}
	
	// Update
	@PatchMapping("/{reviewNo}")
	public ResponseEntity<ApiResponse<Void>> editPlantReview (
			@AuthenticationPrincipal CustomUserDetails userDetails
			, @ModelAttribute @Valid PlantReviewDto plantReview
			, @PathVariable(name = "reviewNo") Long reviewNo
			){
		Long memberNo = userDetails.getMemberNo();
		plantReviewService.editPlantReview(plantReview, memberNo, reviewNo);
		return ResponseEntity.status(200).body(ApiResponse.success("edited", null));
	}
	
	// Delete
	@DeleteMapping("/{reviewNo}")
	public ResponseEntity<ApiResponse<Void>> deletePlantReview(
			@AuthenticationPrincipal CustomUserDetails userDetails
			, @PathVariable(name = "reviewNo") Long reviewNo
			){
		Long memberNo = userDetails.getMemberNo();
		plantReviewService.deletePlantReview(memberNo, reviewNo);
		return ResponseEntity.status(204).body(ApiResponse.noContent("deleted", null));
	}
	
	@DeleteMapping("/{reviewNo}/like")
	public ResponseEntity<ApiResponse<Void>> deletePlantReviewLike (
			@AuthenticationPrincipal CustomUserDetails userDetails
			, @PathVariable(name = "reviewNo") Long reviewNo
			){
		Long memberNo = userDetails.getMemberNo();
		plantReviewService.deletePlantReviewLike(memberNo, reviewNo);
		return ResponseEntity.status(204).body(ApiResponse.created(null));
	}
}
