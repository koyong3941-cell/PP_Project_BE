package com.kh.pp.sensor.model.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SensorDto {
	private Long sensorNo;
	private Long infoNo;
	private Date measureDate;
	private Double humidity;
	private Double temperature;
	private Long spo2;
	private Long co2;
}
