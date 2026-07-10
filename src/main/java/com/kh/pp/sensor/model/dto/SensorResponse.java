package com.kh.pp.sensor.model.dto;

import java.time.LocalDateTime;

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
public class SensorResponse {
	private Long sensorNo;
	private Long infoNo;
	private LocalDateTime measureDate;
	private Double humidity;
	private Double temperature;
	private Long spo2;
	private Long co2;
}
