package com.kh.pp.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.member.model.dto.MemberDto;
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
	

	
}
