package com.kh.pp.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.pp.member.model.dto.MemberDto;

@Mapper
public interface AdminMemberMapper {

	// Read
	List<MemberDto> findAdminAll(@Param("offset") int offset, @Param("size") int size);

	List<MemberDto> findMemberAll(@Param("offset") int offset, @Param("size") int size);
	
	List<MemberDto> findAdminByKeyword(
			@Param("offset") int offset
			, @Param("size") int size
			, @Param("keywordList") List<String> keywordList
			, @Param("target") String target);
	
	List<MemberDto> findMemberByKeyword(
			@Param("offset") int offset
			, @Param("size") int size
			, @Param("keywordList") List<String> keywordList
			, @Param("target") String target);

	// Update
	int restoreAdmins(@Param("memberNos") List<Long> memberNos);

	int restoreMembers(@Param("memberNos") List<Long> memberNos);

	// Delete
	int deleteAdmins(@Param("memberNos") List<Long> memberNos);
	
	int deleteMembers(@Param("memberNos") List<Long> memberNos);
	
	// Count
	int getAdminTotalElements();

	int getMemberTotalElements();
	
	int getAdminTotalElementsByKeyword(
			@Param("keywordList") List<String> keywordList
			, @Param("target") String target);

	int getMemberTotalElementsByKeyword(
			@Param("keywordList") List<String> keywordList
			, @Param("target") String target);



}
