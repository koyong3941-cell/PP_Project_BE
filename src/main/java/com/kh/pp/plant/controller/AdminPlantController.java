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
import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.service.PlantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/plants")
public class AdminPlantController {
	private final PlantService plantService; 
	
	// Create
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> savePlant(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute @Valid PlantDto plant){
		Long memberNoFromToken = userDetails.getMemberNo();
		plant.setMemberNo(memberNoFromToken);
		
		plantService.savePlant(plant);
		return ResponseEntity.status(201).body(ApiResponse.created(null));
	}
	
	// Read (현재 일반 사용자와 똑같이 작동 중)
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<PlantDto>>> findPlantAll(@RequestParam(value = "page", defaultValue ="0") int page){
		PageResponse<PlantDto> plants = plantService.findPlantAll(page);
	
		return ResponseEntity.status(200).body(ApiResponse.success(plants));
	}
	
	@GetMapping("/{plantNo}")
	public ResponseEntity<ApiResponse<PlantDto>> plantDetail(@PathVariable(name = "plantNo") Long plantNo){
		
		PlantDto plant = plantService.plantDetail(plantNo);

		return ResponseEntity.status(200).body(ApiResponse.success(plant));
	}
	
	// Update
	@PatchMapping("/{plantNo}")
	public ResponseEntity<ApiResponse<Void>> editPlant(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute  @Valid PlantDto plant,
			@PathVariable(name = "plantNo") Long plantNo){
		Long memberNoFromToken = userDetails.getMemberNo();
		plant.setMemberNo(memberNoFromToken);
		plant.setPlantNo(plantNo);
		plantService.editPlant(plant);
		return ResponseEntity.status(200).body(ApiResponse.success("edited", null));
	}
	
	// Delete
	@DeleteMapping("/{plantNo}")
	public ResponseEntity<ApiResponse<Void>> deletePlant(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable(name = "plantNo") Long plantNo){
		Long memberNoFromToken = userDetails.getMemberNo();
		plantService.deletePlant(plantNo, memberNoFromToken);
		return ResponseEntity.status(204).body(ApiResponse.noContent("deleted", null));
	}
}
