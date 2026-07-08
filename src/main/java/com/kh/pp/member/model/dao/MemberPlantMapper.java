package com.kh.pp.member.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.member.model.dto.MemberPlantOwnedResponseDto;

@Mapper
public interface MemberPlantMapper {

	// 회원이 해당 식물을 보유하고 있는지 + 보유 개수 조회
	@Select("""
			SELECT
				MEMBER_NO
				,PLANT_NO
				,SMALL_PLANT
				,MIDDLE_PLANT
				,BIG_PLANT
			FROM
				MEMBER_PLANT
			WHERE
				MEMBER_NO = #{memberNo}
			AND
				PLANT_NO = #{plantNo}
			""")
	MemberPlantOwnedResponseDto memberPlantDetail(@Param("memberNo") Long memberNo, @Param("plantNo") Long plantNo);

	// 식물 추가
	@Insert("""
			INSERT
			INTO
				MEMBER_PLANT
				(
				MEMBER_NO
				,PLANT_NO
				,SMALL_PLANT
				,MIDDLE_PLANT
				,BIG_PLANT
				)
			VALUES
				(
				#{memberNo}
				,#{plantNo}
				,#{small}
				,#{middle}
				,#{big}
				)
			""")
	int memberPlantAdd(@Param("memberNo") Long memberNo, @Param("plantNo") Long plantNo,
			@Param("small") Integer small, @Param("middle") Integer middle, @Param("big") Integer big);

	// 식물 개수 수정
	@Update("""
			UPDATE
				MEMBER_PLANT
			SET
				SMALL_PLANT = #{small}
				,MIDDLE_PLANT = #{middle}
				,BIG_PLANT = #{big}
			WHERE
				MEMBER_NO = #{memberNo}
			AND
				PLANT_NO = #{plantNo}
			""")
	int memberPlantEdit(@Param("memberNo") Long memberNo, @Param("plantNo") Long plantNo,
			@Param("small") Integer small, @Param("middle") Integer middle, @Param("big") Integer big);

	// 식물 삭제
	@Delete("""
			DELETE
			FROM
				MEMBER_PLANT
			WHERE
				MEMBER_NO = #{memberNo}
			AND
				PLANT_NO = #{plantNo}
			""")
	int memberPlantDelete(@Param("memberNo") Long memberNo, @Param("plantNo") Long plantNo);
}
