package com.kh.pp.auth.model.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.pp.auth.model.dao.AuthMapper;
import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.member.model.dto.MemberDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final AuthMapper authMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberDto member = authMapper.loadUser(username);
		 
		log.info("조회된 정보 : {}", member);
		
		if(member == null) {
			throw new UsernameNotFoundException("유저 ID 조회 실패");
		}
		
		return CustomUserDetails.builder().memberNo(member.getMemberNo())
											.username(member.getMemberId())
											.password(member.getMemberPwd())
											.authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getRole())))
											.status(member.getDelYn())
											.build();
	}

}
