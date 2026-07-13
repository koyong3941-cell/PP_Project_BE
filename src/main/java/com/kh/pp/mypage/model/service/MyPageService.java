package com.kh.pp.mypage.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.pp.common.page.PageResponse;
import com.kh.pp.common.page.PlantPageResponse;
import com.kh.pp.exception.FailUserRequestException;
import com.kh.pp.mypage.model.PlantSize;
import com.kh.pp.mypage.model.dao.MyPageMapper;
import com.kh.pp.mypage.model.dto.MyPagePlantDetail;
import com.kh.pp.mypage.model.dto.MyPageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {
	private final MyPageMapper myPageMapper;
	
	private int plantCount(Long memberNo) {
		int totalElements = myPageMapper.plantTotalElements(memberNo);
		
		if(totalElements < 1) {
			throw new FailUserRequestException("조회된 결과가 없습니다");
		}
		return totalElements;
	}
	
	public PlantPageResponse<MyPageResponse> plantList(Long memberNo, int size) {
		int totalElements = plantCount(memberNo);
		
		List<MyPageResponse> plantList = myPageMapper.plantList(memberNo, size);
		
		if(plantList.isEmpty()) {
			throw new FailUserRequestException("조회된 결과가 없습니다");
		}
		
		double totalSmall = 0;
		double totalMiddle = 0;
		double totalBig = 0;
		
		for (MyPageResponse plant : plantList) {

		    double capture = plant.getCarbonCapture();

		    double small = capture * PlantSize.smallPlant.getCoefficient() * plant.getSmallPlant();
		    double middle = capture * PlantSize.middlePlant.getCoefficient() * plant.getMiddlePlant();
		    double big = capture * PlantSize.bigPlant.getCoefficient() * plant.getBigPlant();

		    plant.setSmallPlantCap(small);
		    plant.setMiddlePlantCap(middle);	
		    plant.setBigPlantCap(big);

		    totalSmall += small;
		    totalMiddle += middle;
		    totalBig += big;
		}
		// 총량 계산
		Double allCap = totalSmall + totalMiddle + totalBig;
		
		PlantPageResponse<MyPageResponse> response =
		        new PlantPageResponse<>(plantList, totalElements, size, totalSmall, totalMiddle, totalBig, allCap);
		
		return response;
	}

	public PageResponse<MyPagePlantDetail> memberPlantList(Long memberNo, int page) {
		int size = 9;
		int offset = page * size;
		
		int totalElements = plantCount(memberNo);
			
		List<MyPagePlantDetail> plants =  myPageMapper.memberPlantList(memberNo, size, offset);
		
		if(plants.isEmpty()) {
			throw new FailUserRequestException("조회된 결과가 없습니다");
		}
				
		return new PageResponse<>(plants, totalElements, page, size);
	}

}
