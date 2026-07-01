package com.kh.pp.member.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.member.model.dto.MemberEditValidation;
import com.kh.pp.member.model.dto.MemberRequestDto;
import com.kh.pp.member.model.vo.Member;

@Mapper
public interface MemberMapper {
	@Select("SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID = #{memberId}")
	int countByMemberId(String memberId);

	@Insert("INSERT INTO MEMBER(MEMBER_ID, MEMBER_PWD, MEMBER_NAME, ROLE, EMAIL) VALUES(#{memberId}, #{memberPwd}, #{memberName},"
			+ " #{role} ,#{email})")
	int signUp(Member memberEntity);

	@Select("""
			SELECT 
				MEMBER_NAME
				,EMAIL	
			FROM
					MEMBER
			WHERE 
					MEMBER_NO =#{memberNo}
			""")
	MemberRequestDto memberMoreDetails(Long memberNo);

	@Update("""
			UPDATE
			
			SET
			
			WHERE
			""")
	MemberEditValidation userEdit(@Param("memberNo") Long memberNo, @Param("validedMember") MemberEditValidation validedMember);

	@Update("""
			Update
				MEMBER
			SET
				DEL_YN = 'Y'
				,EDIT_DATE = SYSDATE
			WHERE 
				MEMBER_NO = #{memberNo}
			AND
				DEL_YN = 'N'
			""")
	int userDelete(Long memberNo);

}
