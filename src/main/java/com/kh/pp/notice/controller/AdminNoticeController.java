package com.kh.pp.notice.controller;

import java.util.List;

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
	public ResponseEntity<ApiResponse<List<NoticeDto>>> findNoticeAll(
			@RequestParam(value="page", defaultValue="0")int page){
		List<NoticeDto> notices = noticeService.findNoticeAll(page);
		return ResponseEntity.ok(
			ApiResponse.success(notices));
	}
	
	//상세조회
	@GetMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<NoticeDto>> findById(
			@PathVariable(name="noticeNo") Long noticeNo){
		
		return ResponseEntity.ok(
				ApiResponse.success(noticeService.findById(noticeNo)));
	}
	
	//검색
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<NoticeDto>>> search(
			@RequestParam(name="keyword") String keyword,
			@RequestParam(defaultValue="0",name="page") int page){
		List<NoticeDto> notices = noticeService.search(keyword,page);
		
		return ResponseEntity.ok(
				ApiResponse.success(notices));
	}
	
	//작성
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> save(
			@AuthenticationPrincipal CustomUserDetails userDetails
		   ,@ModelAttribute NoticeDto notice){
		int memberNoFromToken = userDetails.getMemberNo();
		notice.setMemberNo(memberNoFromToken);
		
		noticeService.save(notice);
		
		return ResponseEntity.status(201).body(ApiResponse.created("save",null));
		
	}
	
	//수정
	@PatchMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<Void>> update(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@ModelAttribute NoticeDto notice,
			@PathVariable(name = "noticeNo") Long noticeNo){
		int memberNoFromToken = userDetails.getMemberNo();
		noticeService.update(notice);
		
		return ResponseEntity.status(200).body(ApiResponse.success("update",null));
	}
	
	
	//삭제
	@DeleteMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<Void>> delete(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@ModelAttribute NoticeDto notice,
			@PathVariable(name = "boardNo") Long noticeNo){
		
		return ResponseEntity.ok(ApiResponse.success(null));
	}
	
}
