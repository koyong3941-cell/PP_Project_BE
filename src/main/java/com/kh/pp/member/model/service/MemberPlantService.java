package com.kh.pp.member.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.exception.FailUserRequestException;
import com.kh.pp.exception.InvalidMemberInfoException;
import com.kh.pp.exception.PlantNotFoundException;
import com.kh.pp.member.model.dao.MemberPlantMapper;
import com.kh.pp.member.model.dto.MemberPlantCountDto;
import com.kh.pp.member.model.dto.MemberPlantOwnedResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPlantService {
	private final MemberPlantMapper memberPlantMapper;

	// 토큰의 memberNo와 URL PathVariable의 memberNo 일치 여부 검증
	private void validateMemberMatch(Long tokenMemberNo, Long pathMemberNo) {
		if (tokenMemberNo == null || !tokenMemberNo.equals(pathMemberNo)) {
			throw new InvalidMemberInfoException("로그인 상태가 불안정합니다. 다시 로그인 해주세요.");
		}
	}

	// 식물 보유 여부 및 개수 조회
	public MemberPlantOwnedResponseDto memberPlantDetail(Long tokenMemberNo, Long memberNo, Long plantNo) {
		validateMemberMatch(tokenMemberNo, memberNo);

		MemberPlantOwnedResponseDto memberPlant = memberPlantMapper.memberPlantDetail(memberNo, plantNo);

		if (memberPlant == null) {
			throw new PlantNotFoundException("보유한 식물이 아닙니다");
		}

		return memberPlant;
	}

	// 식물 추가 (이미 보유중인 식물이면 수정으로 처리)
	@Transactional
	public void memberPlantAdd(Long tokenMemberNo, Long memberNo, Long plantNo, MemberPlantCountDto memberPlantCount) {
		validateMemberMatch(tokenMemberNo, memberNo);

		// count(*)로 해당 유저가 이미 이 식물을 하나라도 보유중이면 PATCH(수정) 조건으로 전환
		MemberPlantOwnedResponseDto existing = memberPlantMapper.memberPlantDetail(memberNo, plantNo);
		if (existing != null) {
			memberPlantEdit(tokenMemberNo, memberNo, plantNo, memberPlantCount);
			return;
		}

		int result = memberPlantMapper.memberPlantAdd(memberNo, plantNo,
				memberPlantCount.getSmall(), memberPlantCount.getMiddle(), memberPlantCount.getBig());

		if (result != 1) {
			throw new FailUserRequestException("식물 추가에 실패했습니다, 다시 시도해주세요.");
		}
	}

	// 식물 개수 수정
	@Transactional
	public void memberPlantEdit(Long tokenMemberNo, Long memberNo, Long plantNo, MemberPlantCountDto memberPlantCount) {
		validateMemberMatch(tokenMemberNo, memberNo);

		int result = memberPlantMapper.memberPlantEdit(memberNo, plantNo,
				memberPlantCount.getSmall(), memberPlantCount.getMiddle(), memberPlantCount.getBig());

		if (result < 1) {
			throw new FailUserRequestException("식물 변경에 실패했습니다, 다시 시도해주세요.");
		}
	}

	// 식물 삭제
	@Transactional
	public void memberPlantDelete(Long tokenMemberNo, Long memberNo, Long plantNo) {
		validateMemberMatch(tokenMemberNo, memberNo);

		int result = memberPlantMapper.memberPlantDelete(memberNo, plantNo);

		if (result < 1) {
			throw new FailUserRequestException("식물 삭제에 실패했습니다, 다시 시도해주세요.");
		}
	}
}
