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
import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/admins/notices")
@RequiredArgsConstructor
public class AdminNoticeController {

	private final NoticeService noticeService;
	
	/*
	 * //목록조회
	 * 
	 * @GetMapping public ResponseEntity<ApiResponse<PageResponse<NoticeDto>>>
	 * findNoticeAll(
	 * 
	 * @RequestParam(value="page", defaultValue="0")int page){
	 * PageResponse<NoticeDto> notices = noticeService.findNoticeAll(page); return
	 * ResponseEntity.ok( ApiResponse.success(notices)); }
	 * 
	 * //상세조회
	 * 
	 * @GetMapping("/{noticeNo}") public ResponseEntity<ApiResponse<NoticeDto>>
	 * NoticeDetail(
	 * 
	 * @PathVariable(name="noticeNo") Long noticeNo){
	 * 
	 * return ResponseEntity.ok(
	 * ApiResponse.success(noticeService.NoticeDetail(noticeNo))); }
	 * 
	 * //검색
	 * 
	 * @GetMapping("/search") public ResponseEntity<ApiResponse<List<NoticeDto>>>
	 * NoticeSearch(
	 * 
	 * @RequestParam(name="keyword") String keyword,
	 * 
	 * @RequestParam(defaultValue="0",name="page") int page){ List<NoticeDto>
	 * notices = noticeService.NoticeSearch(keyword,page);
	 * 
	 * return ResponseEntity.ok( ApiResponse.success(notices)); }
	 * 
	 * //작성
	 * 
	 * @PostMapping public ResponseEntity<ApiResponse<Void>> saveNotice(
	 * 
	 * @ModelAttribute @Valid NoticeDto notice,
	 * 
	 * @AuthenticationPrincipal CustomUserDetails userDetails){
	 * 
	 * Long memberNoFromToken = userDetails.getMemberNo();
	 * notice.setMemberNo(memberNoFromToken);
	 * 
	 * 
	 * noticeService.saveNotice(notice); return
	 * ResponseEntity.status(201).body(ApiResponse.success("save",null));
	 * 
	 * }
	 * 
	 * //수정
	 * 
	 * @PatchMapping("/{noticeNo}") public ResponseEntity<ApiResponse<Void>>
	 * editNotice(
	 * 
	 * @Valid NoticeDto notice,
	 * 
	 * @AuthenticationPrincipal CustomUserDetails userDetails,
	 * 
	 * @PathVariable(name = "noticeNo") Long noticeNo){
	 * 
	 * Long memberNoFromToken = userDetails.getMemberNo();
	 * noticeService.editNotice(notice,memberNoFromToken,noticeNo);
	 * 
	 * 
	 * return ResponseEntity.status(200).body(ApiResponse.success("edited",null)); }
	 * 
	 * 
	 * //삭제
	 * 
	 * @DeleteMapping("/{noticeNo}") public ResponseEntity<ApiResponse<Void>>
	 * deleteNotice(
	 * 
	 * @AuthenticationPrincipal CustomUserDetails userDetails,
	 * 
	 * @PathVariable(name = "noticeNo") Long noticeNo){
	 * 
	 * noticeService.deleteNotice(userDetails,noticeNo); return
	 * ResponseEntity.ok(ApiResponse.success(null)); }
	 */
	
}
