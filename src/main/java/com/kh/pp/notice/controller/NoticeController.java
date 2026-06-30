package com.kh.pp.notice.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<NoticeDto>>> findAll(){
		return ResponseEntity.ok(
				ApiResponse.success(noticeService.findAll()));
	}
	
	@GetMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<NoticeDto>> findById(
			@PathVariable Integer noticeNo){
		
		return ResponseEntity.ok(
				ApiResponse.success(noticeService.findById(noticeNo)));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> save(
			@AuthenticationPrincipal CustomUserDetails user,
			@PathVariable Integer noticeNo,
			@RequestBody NoticeDto notice){
	
		noticeService.delete(noticeNo);
		
		return ResponseEntity.noContent().build();
	}
			



}
