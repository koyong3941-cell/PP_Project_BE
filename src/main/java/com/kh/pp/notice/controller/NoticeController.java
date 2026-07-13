package com.kh.pp.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	
	//목록조회
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<NoticeDto>>> findNoticeAll(@RequestParam(value="page", defaultValue="0")int page){
		PageResponse<NoticeDto> notices =  noticeService.findNoticeAll(page);
		return ResponseEntity.ok(
			ApiResponse.success(notices));
		}
	
	//상세조회
	@GetMapping("/{noticeNo}")
	public ResponseEntity<ApiResponse<NoticeDto>> noticeDetail(
			@PathVariable(name= "noticeNo") Long noticeNo){
		
		
		NoticeDto notice = noticeService.noticeDetail(noticeNo);
		
		return ResponseEntity.status(200).body(ApiResponse.success(notice));
	}
	
	//검색
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<NoticeDto>>> searchNotice(
			@RequestParam(name="keyword") String keyword,
			@RequestParam(name = "target", required = false) String target,
			@RequestParam(defaultValue="0", name="page") int page){

		
		List<NoticeDto> notices = noticeService.searchNotice(keyword, target, page);
		
		return ResponseEntity.ok(ApiResponse.success(notices));
	}

}
