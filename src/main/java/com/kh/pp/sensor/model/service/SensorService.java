package com.kh.pp.sensor.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.exception.FailUserRequestException;
import com.kh.pp.sensor.model.dao.SensorMapper;
import com.kh.pp.sensor.model.dto.SensorDto;
import com.kh.pp.sensor.model.dto.SensorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorService {
	private final SensorMapper sensorMapper;
	
	public List<SensorDto> sensorInfoRequest(Long memberNo) {
		List<SensorDto> sensorInfoList = sensorMapper.sensorInfoRequest(memberNo);
		
		if(sensorInfoList == null) {
			throw new FailUserRequestException("센서 정보 조회에 실패하였습니다, 잠시후 다시 시도해주세요.");
		}
		
		return sensorInfoList;
	}

	public void sensorResponse(SensorResponse sensor) {
		int result = sensorMapper.sensorResponse(sensor);
		
		if(result < 1) {
			throw new FailUserRequestException("센서와 통신에 실패하였습니다, 잠시후 다시 시도해주세요.");
		}
		
	}

}
