package com.kh.pp.member.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.member.model.vo.Member;

@Mapper
public interface MemberMapper {

	@Select("SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID = #{memberId}")
	int countByMemberId(String memberId);

	@Insert("INSERT INTO MEMBER(MEMBER_ID, MEMBER_PWD, MEMBER_NAME, ROLE, EMAIL) VALUES(#{memberId}, #{memberPwd}, #{memberName},"
			+ " #{role} ,#{email})")
	int signUp(Member memberEntity);
	

}
