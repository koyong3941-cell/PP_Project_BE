package com.kh.pp.plant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.service.PlantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plants")
public class PlantController {
	private final PlantService plantService; 
	
	// Read
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<PlantDto>>> findPlantAll(@RequestParam(value = "page", defaultValue ="0") int page){
		PageResponse<PlantDto> plants = plantService.findPlantAll(page);
	
		return ResponseEntity.status(200).body(ApiResponse.success(plants));
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<PlantDto>>> findBoardByKeyword(@RequestParam(name = "page", defaultValue = "0") int page,
																		  @RequestParam(name = "keyword", required = false) String keyword,
																		  @RequestParam(name = "target", required = false) String target){
		PageResponse<PlantDto> plants = plantService.findPlantByKeyword(page, keyword, target);
		
		return ResponseEntity.ok(ApiResponse.success(plants));
	}
	
	@GetMapping("/{plantNo}")
	public ResponseEntity<ApiResponse<PlantDto>> plantDetail(@PathVariable(name = "plantNo") Long plantNo){
		
		PlantDto plant = plantService.plantDetail(plantNo);

		return ResponseEntity.status(200).body(ApiResponse.success(plant));
	}
	
}
