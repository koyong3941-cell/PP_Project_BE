package com.kh.pp.member.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailSignUpException;
import com.kh.pp.exception.FailUpdateException;
import com.kh.pp.exception.MemberNotFoundException;
import com.kh.pp.file.service.FileService;
import com.kh.pp.member.model.dao.AdminMemberMapper;
import com.kh.pp.member.model.dao.MemberImgMapper;
import com.kh.pp.member.model.dao.MemberMapper;
import com.kh.pp.member.model.dto.MemberDto;
import com.kh.pp.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {
	private final AdminMemberMapper adminMemberMapper;
	private final MemberMapper memberMapper;
	private final MemberImgMapper memberImgMapper;
	private final FileService fileService;
	private final PasswordEncoder passwordEncoder;

	// Create
	@Transactional
	public void createAdmin(MemberDto member) {
		countByMemberId(member.getMemberId());
		
		Member memberEntity = Member.builder()
				.memberId(member.getMemberId())
				.memberPwd(passwordEncoder.encode(member.getMemberPwd()))
				.memberName(member.getMemberName())
				.role("ROLE_ADMIN") 
				.email(member.getEmail())
				.build();
		
		int result = memberMapper.signUp(memberEntity); 
		
		if (result != 1) {
		    throw new FailSignUpException("회원가입에 실패했습니다.");
		}
	}
	
	// Read
	public PageResponse<MemberDto> findAdminAll(int page, int size) {
		int offset = page * size;

		int totalElements = adminMemberMapper.getAdminTotalElements();
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<MemberDto> admins = adminMemberMapper.findAdminAll(offset, size);
		
		return new PageResponse<>(admins, totalElements, page, size);
	}

	public PageResponse<MemberDto> findAdminByKeyword(int page, int size, String keyword, String target) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return findAdminAll(page, size);
		}
		
		int offset = page * size;
		
		List<String> keywordList = new ArrayList<>();
		String[] words = keyword.trim().split("\\s+");
		for (String word : words) {
			if (!word.isEmpty()) {
				keywordList.add(word);
			}
		}
		
		if (target == null || target.trim().isEmpty()) {
			target = "all";
		}
		
		int totalElements = adminMemberMapper.getAdminTotalElementsByKeyword(keywordList, target);
		
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<MemberDto> admins = adminMemberMapper.findAdminByKeyword(offset, size, keywordList, target);
		
		return new PageResponse<>(admins, totalElements, page, size);
	}
	
	// Update
	@Transactional
	public int restoreAdmins(List<Long> memberNos) {
		if (memberNos == null || memberNos.isEmpty()) {
			throw new MemberNotFoundException("복구시킬 회원 번호를 선택해주세요.");
		}
		
		int result = adminMemberMapper.restoreAdmins(memberNos);

		if (result == 0) {
			throw new FailUpdateException("복구에 실패하였습니다.");
		}
		return result;
	}
	
	// Delete
	@Transactional
	public int deleteAdmins(List<Long> memberNos) {
		if (memberNos == null || memberNos.isEmpty()) {
			throw new MemberNotFoundException("탈퇴시킬 회원 번호를 선택해주세요.");
		}
		
		int result = adminMemberMapper.deleteAdmins(memberNos);

		if (result == 0) {
			throw new FailUpdateException("탈퇴에 실패하였습니다.");
		}
		return result;
	}
	
	// Validate
	private void countByMemberId(String memberId) { 
		int result = memberMapper.countByMemberId(memberId);
		if(result > 0 ) { 
			throw new FailSignUpException("등록된 아이디가 존재합니다.");
		}
	}


}
