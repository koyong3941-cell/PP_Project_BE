package com.kh.pp.sensor.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.pp.sensor.model.dto.SensorDto;

@Mapper
public interface SensorMapper {
	
	@Select("""
			SELECT
				S.SENSOR_NO
				,I.INFO_NO
				,I.MEASURE_DATE
				,I.HUMIDITY
				,I.TEMPERATURE
				,I.SPO2
				,I.CO2
			FROM
				SENSOR S
			JOIN
				SENSOR_INFO I ON S.SENSOR_NO = I.SENSOR_NO
			WHERE
				S.MEMBER_NO=#{memberNo}
			ORDER BY 
				I.MEASURE_DATE DESC
			FETCH 
				FIRST 7 ROWS ONLY
			""")
	List<SensorDto> sensorInfoRequest(Long memberNo);

}
