package com.kh.pp.token.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.token.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {

	@Insert("INSERT INTO ANNO_TOKEN VALUES (#{adminId}, #{token}, #{expiration})")
	void saveToken(RefreshToken token);
	
	@Delete("DELETE FROM ANNO_TOKEN WHERE ADMIN_ID = #{adminId}")
	void deleteToken(String adminId);
	
	@Select("SELECT ADMIN_ID, TOKEN, EXPIRATION FROM ANNO_TOKEN WHERE TOKEN = #{token}")
	RefreshToken findByToken(String token);
}
