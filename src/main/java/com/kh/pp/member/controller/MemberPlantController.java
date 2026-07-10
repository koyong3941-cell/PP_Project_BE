package com.kh.pp.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.member.model.dto.MemberPlantOwnedResponseDto;
import com.kh.pp.member.model.service.MemberPlantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members/{memberNo}/plants/{plantNo}")
@RestController
@RequiredArgsConstructor
public class MemberPlantController {
	private final MemberPlantService memberPlantService;

	// 식물 상세 조회 (보유 여부 확인)
	@GetMapping
	public ResponseEntity<ApiResponse<MemberPlantOwnedResponseDto>> memberPlantDetail(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable("memberNo") Long memberNo,
			@PathVariable("plantNo") Long plantNo) {

		MemberPlantOwnedResponseDto result = memberPlantService.memberPlantDetail(userDetails.getMemberNo(), memberNo, plantNo);

		return ResponseEntity.status(200).body(ApiResponse.success("회원 정보 일치", result));
	}

	// 식물 추가
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> memberPlantAdd(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable("memberNo") Long memberNo,
			@PathVariable("plantNo") Long plantNo,
			@Valid @RequestBody MemberPlantOwnedResponseDto plantRequest) {

		memberPlantService.memberPlantAdd(userDetails.getMemberNo(), memberNo, plantNo, plantRequest);
		return ResponseEntity.status(201).body(ApiResponse.created("식물 추가에 성공했습니다.", null));
	} 

	// 식물 수정
	@PatchMapping
	public ResponseEntity<ApiResponse<Void>> memberPlantEdit(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable("memberNo") Long memberNo,
			@PathVariable("plantNo") Long plantNo,
			@Valid @RequestBody MemberPlantOwnedResponseDto plantRequest) {

		memberPlantService.memberPlantEdit(userDetails.getMemberNo(), memberNo, plantNo, plantRequest);
		return ResponseEntity.status(200).body(ApiResponse.success("식물 변경에 성공했습니다.", null));
	}

	// 식물 삭제
	@DeleteMapping
	public ResponseEntity<ApiResponse<String>> memberPlantDelete(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable("memberNo") Long memberNo,
			@PathVariable("plantNo") Long plantNo) {

		memberPlantService.memberPlantDelete(userDetails.getMemberNo(), memberNo, plantNo);
		return ResponseEntity.status(200).body(ApiResponse.success("식물 삭제에 성공했습니다", null));
	}

}
