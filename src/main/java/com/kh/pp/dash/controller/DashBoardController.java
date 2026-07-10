package com.kh.pp.dash.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.dash.model.DashBoardService;
import com.kh.pp.dash.model.dto.DashBoardDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/dashboard")
public class DashBoardController {
	private final DashBoardService dashBoardService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<DashBoardDto>> dashBoardResponse(){
		
		DashBoardDto dash = dashBoardService.dashBoardResponse();
		
		return ResponseEntity.status(200).body(ApiResponse.success("조회성공", dash));
	}

}
