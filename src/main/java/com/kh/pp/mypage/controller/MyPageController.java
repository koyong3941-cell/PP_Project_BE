package com.kh.pp.mypage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.common.page.PlantPageResponse;
import com.kh.pp.mypage.model.dto.MyPagePlantDetail;
import com.kh.pp.mypage.model.dto.MyPageResponse;
import com.kh.pp.mypage.model.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {
	private final MyPageService myPageSerivce;

	@GetMapping
	public ResponseEntity<ApiResponse<PlantPageResponse<MyPageResponse>>> plantList(@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam(name = "size") int size){
		
		PlantPageResponse<MyPageResponse> pageDto = myPageSerivce.plantList(userDetails.getMemberNo(), size);
		
		return ResponseEntity.ok(ApiResponse.success(pageDto));
	}
	
	@GetMapping("/plantlist")
	public ResponseEntity<ApiResponse<PageResponse<MyPagePlantDetail>>> memberPlantList(@AuthenticationPrincipal CustomUserDetails userDetails,
			 @RequestParam(name = "page") int page){
		
		PageResponse<MyPagePlantDetail> pageDto = myPageSerivce.memberPlantList(userDetails.getMemberNo(), page);
		
		return ResponseEntity.ok(ApiResponse.success("조회 성공",pageDto));
	}
}
