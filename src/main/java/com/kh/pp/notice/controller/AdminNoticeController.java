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
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			@Valid NoticeDto notice,
			@RequestParam(name="file", required=false) MultipartFile file,
			@AuthenticationPrincipal CustomUserDetails userDetails){
		
		
		log.info("게시글 정보 : {} / 파일정보 : {}",notice,file.getOriginalFilename(),userDetails.getUsername());
		
		return ResponseEntity.status(201).body(ApiResponse.success("save",null));
		
	}
	
	//수정
	@PatchMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<Void>> update(
			@Valid NoticeDto notice,
			@RequestParam(name="file",required=false) MultipartFile file,
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable(name = "noticeNo") Long noticeNo){
	
		noticeService.update(notice,file,userDetails,noticeNo);
		
		
		return ResponseEntity.status(200).body(ApiResponse.success("update",null));
	}
	
	
	//삭제
	@DeleteMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<Void>> delete(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable(name = "noticeNo") Long noticeNo){
		
		noticeService.delete(userDetails,noticeNo);
		return ResponseEntity.ok(ApiResponse.success(null));
	}
	
}
