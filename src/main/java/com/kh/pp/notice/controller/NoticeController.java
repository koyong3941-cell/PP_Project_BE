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

import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {
	private final NoticeService noticeService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<NoticeDto>>> findNoticeAll(@RequestParam(value = "page", defaultValue ="0") int page){
		List<NoticeDto> notices = noticeService.findNoticeAll(page);
		
		return ResponseEntity.status(200).body(ApiResponse.success(notices));
	}
	
	@GetMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<NoticeDto>> noticeDetail(@PathVariable(name = "noticeNo") Long noticeNo){
		NoticeDto notice = noticeService.noticeDetail(noticeNo);
		
		return ResponseEntity.status(200).body(ApiResponse.success(notice));
	}
	
	
}
