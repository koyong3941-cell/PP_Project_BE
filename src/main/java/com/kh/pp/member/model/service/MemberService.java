package com.kh.pp.member.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.exception.FailSignUpException;
import com.kh.pp.member.model.dao.MemberMapper;
import com.kh.pp.member.model.dto.MemberDto;
import com.kh.pp.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	
	public void signUp(MemberDto member) {
		countByMemberId(member.getMemberId());
		
		log.info(member.getMemberId());
		
		Member memberEntity = Member.builder()
				.memberId(member.getMemberId())
				.memberPwd(passwordEncoder.encode(member.getMemberPwd()))
				.memberName(member.getMemberName())
				.role("ROLE_USER") 
				.email(member.getEmail())
				.build();
		
		int result = memberMapper.signUp(memberEntity); 
	}
	
	private void countByMemberId(String memberId) {
		int result = memberMapper.countByMemberId(memberId);
		
		log.info(memberId);
		
		if(result > 0 ) { 
			throw new FailSignUpException("등록된 아이디가 존재합니다.");
		}
	}
	

}
