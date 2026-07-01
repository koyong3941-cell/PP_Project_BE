package com.kh.pp.member.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.exception.FailSignUpException;
import com.kh.pp.exception.FailUserRequestException;
import com.kh.pp.member.model.dao.MemberMapper;
import com.kh.pp.member.model.dto.MemberDto;
import com.kh.pp.member.model.dto.MemberEditValidation;
import com.kh.pp.member.model.dto.MemberRequestDto;
import com.kh.pp.member.model.vo.Member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	
	//멤버 등록
	public void signUp(MemberDto member) {
		countByMemberId(member.getMemberId());
		
		Member memberEntity = Member.builder()
				.memberId(member.getMemberId())
				.memberPwd(passwordEncoder.encode(member.getMemberPwd()))
				.memberName(member.getMemberName())
				.role("ROLE_USER") 
				.email(member.getEmail())
				.build();
		
		int result = memberMapper.signUp(memberEntity); 
	}
	// 멤버 검증
	private void countByMemberId(String memberId) { //.
		int result = memberMapper.countByMemberId(memberId);
		if(result > 0 ) { 
			throw new FailSignUpException("등록된 아이디가 존재합니다.");
		}
	}

	// 멤버 상세조회 *앞단에서 로그인 시 추가 정보를 페이로드에 담지 않고 스토리지 저장함
	public MemberRequestDto memberMoreDetails(Long memberNo) {
		MemberRequestDto  memberRequestResult = memberMapper.memberMoreDetails(memberNo);
		if(memberRequestResult == null) {
			throw new FailUserRequestException("사용자 정보 요청에  실패하였습니다.");
		}
		return memberRequestResult;
	}
	
	// 멤버 수정
	@Transactional
	public MemberEditValidation userEdit(Long memberNo, MemberEditValidation validedMember) {
		
		MemberEditValidation memberEditedResult = memberMapper.userEdit(memberNo, validedMember);
		
		if(memberEditedResult == null){
			throw new FailUserRequestException("사용자 수정에  실패하였습니다, 다시 시도해주세요.");
		}
		return memberEditedResult;
	}
	
	// 멤버 삭제
	@Transactional
	public void userDelete(Long memberNo) {
		int memberDeletedResult = memberMapper.userDelete(memberNo);

		if(memberDeletedResult < 0){
			throw new FailUserRequestException("사용자 수정에  실패하였습니다, 다시 시도해주세요.");
		}
	}
	

}
