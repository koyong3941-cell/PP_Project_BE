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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.notice.model.dto.AdminNoticeDto;
import com.kh.pp.notice.model.dto.NoticeNoListDto;
import com.kh.pp.notice.model.service.AdminNoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/admins/notices")
@RequiredArgsConstructor
public class AdminNoticeController {
	private final AdminNoticeService adminNoticeService;
	
	 //목록조회
	 @GetMapping public ResponseEntity<ApiResponse<PageResponse<AdminNoticeDto>>>findNoticeAll(@RequestParam(value="page", defaultValue="0")int page){
		  PageResponse<AdminNoticeDto> notices = adminNoticeService.findNoticeAll(page); 
		  
		  return ResponseEntity.ok(ApiResponse.success(notices)); 
	 }
	
	 @GetMapping("/{noticeNo}")
	 public ResponseEntity<ApiResponse<AdminNoticeDto>> NoticeDetail(@PathVariable(name="noticeNo") Long noticeNo){
	  
		 AdminNoticeDto notice = adminNoticeService.noticeDetail(noticeNo);
		 
		  return ResponseEntity.status(200).body(ApiResponse.success("조회성공", notice)); 
	 }
	  
	//검색 
	 @GetMapping("/search") 
	 public ResponseEntity<ApiResponse<List<AdminNoticeDto>>> NoticeSearch(@RequestParam(name="keyword") String keyword, @RequestParam(defaultValue="0",name="page") int page){ 
			
		  List<AdminNoticeDto>
		  notices = adminNoticeService.noticeSearch(keyword,page);
		  
		 return ResponseEntity.status(200).body(ApiResponse.success("조회 성공",notices)); 
	 }
	 	  
	  @PostMapping 
	  public ResponseEntity<ApiResponse<Void>> saveNotice( @ModelAttribute @Valid AdminNoticeDto notice, @AuthenticationPrincipal CustomUserDetails userDetails){
		  Long memberNoFromToken = userDetails.getMemberNo();
		  notice.setMemberNo(memberNoFromToken);
		  
		 adminNoticeService.saveNotice(notice); 
		  
		  return ResponseEntity.status(201).body(ApiResponse.success("등록 성공",null));
	  }
	 
	 // 수정 
	 @PatchMapping("/{noticeNo}")
	 public ResponseEntity<ApiResponse<Void>> editNotice(@Valid AdminNoticeDto notice, @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable(name = "noticeNo") Long noticeNo){
	  
	 Long memberNoFromToken = userDetails.getMemberNo();
	 adminNoticeService.editNotice(notice,memberNoFromToken,noticeNo);
	 
	  return ResponseEntity.status(200).body(ApiResponse.success("수정 성공",null)); }
	 
	@DeleteMapping
	public ResponseEntity<ApiResponse<Integer>> deleteNotice(@RequestBody NoticeNoListDto request){
		System.out.println("받은 데이터: " + request.getNoticeNos());
		int result = adminNoticeService.deleteNotice(request.getNoticeNos()); 
		return ResponseEntity.status(200).body(ApiResponse.success("삭제 성공", result)); 
	}
	
}
