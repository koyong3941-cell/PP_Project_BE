package com.kh.pp.mypage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.common.page.PlantPageResponse;
import com.kh.pp.mypage.model.dto.MyPageResponse;
import com.kh.pp.mypage.model.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {
	private final MyPageService myPageSerivce;

	@GetMapping
	public ResponseEntity<ApiResponse<PlantPageResponse<MyPageResponse>>> plantList(@AuthenticationPrincipal CustomUserDetails userDetails){
		
		PlantPageResponse<MyPageResponse> pageDto = myPageSerivce.plantList(userDetails.getMemberNo());
		
		return ResponseEntity.ok(ApiResponse.success(pageDto));
	}
}
