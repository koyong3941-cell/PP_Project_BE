package com.kh.pp.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.member.model.dto.MemberDto;
import com.kh.pp.member.model.dto.MemberEditValidation;
import com.kh.pp.member.model.dto.MemberRequestDto;
import com.kh.pp.member.model.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members")
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<String>> signUp(@Valid @RequestBody MemberDto member){
		memberService.signUp(member);
		String message = "가입에 성공하였습니다~";
	return ResponseEntity.status(201).body(ApiResponse.created("가입 성공", message));
	}
	
	@GetMapping("/detail")
	public ResponseEntity<ApiResponse<MemberRequestDto>> memberMoreDetails(@AuthenticationPrincipal CustomUserDetails userDetails){
		
		Long memberNoFromToken = userDetails.getMemberNo();
		MemberRequestDto memberInfo = memberService.memberMoreDetails(memberNoFromToken);
		return ResponseEntity.status(200).body(ApiResponse.success("조회 성공", memberInfo));
	}
	
	@PatchMapping("/edit")
	public ResponseEntity<ApiResponse<MemberEditValidation>> userEdit(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid MemberEditValidation validedMember){
		Long memberNoFromToken = userDetails.getMemberNo();
		MemberEditValidation memberEditedInfo = memberService.userEdit(memberNoFromToken, validedMember);
		return ResponseEntity.status(200).body(ApiResponse.success("수정 성공", memberEditedInfo));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<Void>> userDelete(@AuthenticationPrincipal CustomUserDetails userDetails){
		Long memberNoFromToken = userDetails.getMemberNo();
		memberService.userDelete(memberNoFromToken);
		return ResponseEntity.status(200).body(ApiResponse.success("삭제 성공", null));
	}

	
}
