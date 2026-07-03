package com.kh.pp.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.member.model.dto.MemberImgDto;

@Mapper
public interface MemberImgMapper {
	@Select("""
			SELECT 
				IMG_NO
				,MEMBER_NO
			FROM
				MEMBER_IMG
			WHERE
				MEMBER_NO = #{memberNo}
			AND
				DEL_YN = 'N'
			""")
	List<MemberImgDto> memberImgCount(Long memberNo);

	@Select("""
			SELECT
				MAX(IMG_NO)
			FROM 
				MEMBER_IMG
			WHERE 
				MEMBER_NO = #{memberNo}
			AND
				DEL_YN = 'N'
			""")
	Long findMaxCount(Long memberNo);
	
	@Update("""
			UPDATE
				MEMBER_IMG
			SET
				DEL_YN = 'Y'
			WHERE
				IMG_NO = #{imgNo}
			AND
				MEMBER_NO = #{memberNo}
			""")
	void userImgDelete(@Param("memberNo") Long memberNo, @Param("imgNo") Long imgNo);

	@Insert("""
			INSERT
			INTO
				MEMBER_IMG
				(
				MEMBER_NO
			 	,ORIGINAL_NAME
				,SAVE_NAME
				,IMG_PATH
				)
			VALUES
				(
				#{memberNo}
			 	,#{originalName}
			 	,#{saveName}
			 	,#{imgPath}
				)
			""")
	int userImgUpload(MemberImgDto imgDto);

}
