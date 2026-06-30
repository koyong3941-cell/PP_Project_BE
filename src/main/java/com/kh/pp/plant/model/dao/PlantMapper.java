package com.kh.pp.plant.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.pp.plant.model.dto.PlantDto;
import com.kh.pp.plant.model.vo.Plant;

@Mapper
public interface PlantMapper {
	@Select("""
			SELECT 
				p.PLANT_NO
				,m.MEMBER_NO
				,p.PLANT_NAME
				,p.CLASSIFICATION
				,p.COUNT
				,p.CARBON_CAPTURE
				,p.CREATE_DATE
				,p.PLANT_INFO
				,p.GROWTH_INFO
				,p.PLANT_API
				,p.DEL_YN
			FROM
				PLANT p
			JOIN
				MEMBER m ON (p.MEMBER_NO = m.MEMBER_NO)
			WHERE
				p.DEL_YN = 'N'
				ORDER BY p.PLANT_NO DESC
				OFFSET #{offset} ROWS
				FETCH NEXT #{limit} ROWS ONLY
			""")
	List<PlantDto> findPlantAll(@Param("offset") int offset, @Param("limit") int limit);
	
	@Update("UPDATE PLANT SET COUNT = COUNT + 1 WHERE PLANT_NO = #{plantNo}")
	void increaseCount(Long plantNo);
	
	PlantDto plantDetail(Long plantNo);
	
	int deletePlant(@Param("plantNo")Long plantNo, @Param("memberNo")int memberNo);

	@Insert("""
			INSERT
			  INTO
		  		PLANT	
		  		(
		  		MEMBER_NO 
			  	,PLANT_NAME
			  	,PLANT_INFO
			  		)
			VALUES
					(
				#{memberNo}
				,#{plantName}
				,#{plantInfo}
					)
		""")
	void savePlant(Plant plantEntity);

	void editPlant(@Param("plant")PlantDto plant, @Param("memberNo")int memberNo, @Param("plantNo")Long plantNo);
	
}
