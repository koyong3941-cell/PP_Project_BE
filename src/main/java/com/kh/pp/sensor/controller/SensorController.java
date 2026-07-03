package com.kh.pp.sensor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.sensor.model.dto.SensorDto;
import com.kh.pp.sensor.model.service.SensorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {
	private final SensorService sensorService;
	
	@GetMapping	
	public ResponseEntity<ApiResponse<List<SensorDto>>> sensorInfoRequest(@AuthenticationPrincipal CustomUserDetails userDetails){
		Long memberNoFromToken = userDetails.getMemberNo();
		
		List<SensorDto> sensorInfo = sensorService.sensorInfoRequest(memberNoFromToken);
		
		return ResponseEntity.status(200).body(ApiResponse.success(sensorInfo));
	}
}
