package com.kh.pp.token.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.token.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {

	@Insert("INSERT INTO TOKEN VALUES (#{memberNo}, #{token}, #{expirationToken})")
	void saveToken(RefreshToken token);
	
	@Delete("DELETE FROM TOKEN WHERE MEMBER_NO = #{memberNo}")
	void deleteToken(Long memberNo);
	
	@Select("SELECT MEMBER_NO, TOKEN, EXPIRATION_TOKEN FROM TOKEN WHERE TOKEN = #{token}")
	RefreshToken findByToken(String token);
}
