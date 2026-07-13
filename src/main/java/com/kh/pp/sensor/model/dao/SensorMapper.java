package com.kh.pp.sensor.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.sensor.model.dto.SensorDto;
import com.kh.pp.sensor.model.dto.SensorResponse;

@Mapper
public interface SensorMapper {
	
	@Select("""
			SELECT *
			FROM (
			    SELECT
			        S.SENSOR_NO,
			        I.INFO_NO,
			        I.MEASURE_DATE,
			        I.HUMIDITY,
			        I.TEMPERATURE,
			        I.SPO2,
			        I.CO2
			    FROM 
			    	SENSOR S
			    JOIN 
			    	SENSOR_INFO I ON S.SENSOR_NO = I.SENSOR_NO
			    WHERE 
			    	S.MEMBER_NO = #{memberNo}
			      AND 
			      	I.MEASURE_DATE >= (
			          SELECT MAX(MEASURE_DATE) - 6
			          FROM SENSOR_INFO
			      )
			)
			ORDER BY MEASURE_DATE ASC
			""")
	List<SensorDto> sensorInfoRequest(Long memberNo);

	@Insert("""
			INSERT INTO
				SENSOR_INFO(
				SENSOR_NO
				,MEASURE_DATE
				,HUMIDITY
				,TEMPERATURE
				,SPO2
				,CO2
				)
			VALUES(
				#{sensorNo}
				,#{measuredate}
				,#{humidity}
				,#{temperature}
				,#{spo2}
				,#{co2}
			)
			""")
	int sensorResponse(SensorResponse sensor);

}
