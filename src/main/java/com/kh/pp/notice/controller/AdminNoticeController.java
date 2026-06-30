package com.kh.pp.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/notices")
@RequiredArgsConstructor
public class AdminNoticeController {

	private final NoticeService noticeService;
	
	//목록조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<NoticeDto>>> findAll(){
		return ResponseEntity.ok(
			ApiResponse.success(noticeService.findAll()));
	}
	
	//상세조회
	@GetMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<NoticeDto>> findById(
			@PathVariable Integer noticeNo){
		
		return ResponseEntity.ok(
				ApiResponse.success(noticeService.findById(noticeNo)));
	}
	
	//검색
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<NoticeDto>>> search(
			@RequestParam String keyword){
		
		return ResponseEntity.ok(
				ApiResponse.success(noticeService.findAll()));
	}
	
	//작성
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> save(
			@RequestBody NoticeDto notice){
	
		noticeService.saveNotice(notice);
		
		return ResponseEntity.ok(ApiResponse.success(null));
		
	}
	
	//수정
	
	
	
	//삭제
}
