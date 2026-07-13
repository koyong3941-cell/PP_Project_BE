package com.kh.pp.dash.model;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.pp.dash.dao.DashBoardMapper;
import com.kh.pp.dash.model.dto.DashBoardDto;
import com.kh.pp.exception.FailUserRequestException;
import com.kh.pp.mypage.model.PlantSize;
import com.kh.pp.mypage.model.dto.MyPageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	
public class DashBoardService {
	private final DashBoardMapper dashBoardMapper;
	
	public DashBoardDto dashBoardResponse() {
		
		DashBoardDto graph = dashBoardMapper.dashBoardResponse();
				
		return graph;
	}
	

}
