package com.kh.pp.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.member.model.dto.MemberDto;
import com.kh.pp.member.model.dto.MemberNoListDto;
import com.kh.pp.member.model.service.AdminMemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/admins")
@RestController
@RequiredArgsConstructor
public class AdminMemberController {
	private final AdminMemberService adminMemberService;
	
	// Create
	@PostMapping("/admins")
	public ResponseEntity<ApiResponse<String>> createAdmin(@Valid @RequestBody MemberDto member){
		adminMemberService.createAdmin(member);
		String message = "가입에 성공하였습니다~";
	return ResponseEntity.status(201).body(ApiResponse.created("가입 성공", message));
	}
	
	// Read
	@GetMapping("/admins")
	public ResponseEntity<ApiResponse<PageResponse<MemberDto>>> findAdminAll(
			@RequestParam(value = "page", defaultValue ="0") int page
			, @RequestParam(name = "size", defaultValue = "10") int size
			){
		PageResponse<MemberDto> admins = adminMemberService.findAdminAll(page, size);
	
		return ResponseEntity.status(200).body(ApiResponse.success(admins));
	}
	
	@GetMapping("/admins/search")
	public ResponseEntity<ApiResponse<PageResponse<MemberDto>>> findAdminByKeyword(
			@RequestParam(name = "page", defaultValue = "0") int page
			, @RequestParam(name = "size", defaultValue = "10") int size
			, @RequestParam(name = "keyword", required = false) String keyword
			, @RequestParam(name = "target", required = false) String target
			){
		PageResponse<MemberDto> admins = adminMemberService.findAdminByKeyword(page, size, keyword, target);
		
		return ResponseEntity.ok(ApiResponse.success(admins));
	}
	
	// Update
	@PatchMapping("/admins")
	public ResponseEntity<ApiResponse<?>> restoreAdmins(@RequestBody MemberNoListDto request){
		int result = adminMemberService.restoreAdmins(request.getMemberNos());
		return ResponseEntity.ok(ApiResponse.success(result + "명 복구에 성공했습니다.", result));
	}
	
	// Delete
	@DeleteMapping("/admins")
	public ResponseEntity<ApiResponse<?>> deleteAdmins(@RequestBody MemberNoListDto request){
		int result = adminMemberService.deleteAdmins(request.getMemberNos());
		return ResponseEntity.ok(ApiResponse.success(result + "명 탈퇴에 성공했습니다.", result));
	}
	
}
