package com.kh.pp.mypage.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.mypage.model.dto.MyPageResponse;

@Mapper
public interface MyPageMapper {

	@Select("""
			SELECT 
				M.PLANT_NO
				,P.PLANT_NAME
				,P.CLASSIFICATION
				,M.MEMBER_NO
				,M.BIG_PLANT
				,M.MIDDLE_PLANT
				,M.SMALL_PLANT
				,P.CARBON_CAPTURE
				,PI.IMG_PATH
				,PI.SAVE_NAME
				,PI.DEL_YN
			FROM
				MEMBER_PLANT M
			JOIN
				PLANT P ON M.PLANT_NO = P.PLANT_NO
			LEFT JOIN 
				PLANT_IMG PI
					ON P.PLANT_NO = PI.PLANT_NO
			AND
				PI.DEL_YN = 'N'
			WHERE
				M.MEMBER_NO = #{memberNo}
			ORDER 
			   BY
			   	M.PLANT_NO ASC
			FETCH 
			 	NEXT  #{size} ROWS ONLY	
			""")
	List<MyPageResponse> plantList(@Param("memberNo") Long memberNo, @Param("size")int size);

	@Select("""
			SELECT
				COUNT(*)
			FROM
				MEMBER_PLANT
			WHERE
				MEMBER_NO = #{memberNo}
			""")
	int plantTotalElements(Long memberNo);

}
