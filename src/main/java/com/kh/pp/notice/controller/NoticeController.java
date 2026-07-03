package com.kh.pp.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	
	//목록조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<NoticeDto>>> findNoticeAll(@RequestParam(value="page", defaultValue="0")int page){
		List<NoticeDto> notices =  noticeService.findNoticeAll(page);
		return ResponseEntity.ok(
			ApiResponse.success(notices));
		}
	
	//상세조회
	@GetMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<NoticeDto>> findByNoticeId(
			@PathVariable(name= "noticeNo") Long noticeNo){
		
		return ResponseEntity.ok(
				ApiResponse.success(noticeService.findByNoticeId(noticeNo)));
	}
	
	//검색
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<NoticeDto>>> Noticesearch(
			@RequestParam(name="keyword") String keyword,
			@RequestParam(defaultValue="0", name="page") int page){
		List<NoticeDto> notices = noticeService.Noticesearch(keyword,page);
		
		return ResponseEntity.ok(ApiResponse.success(notices));
	}

}
