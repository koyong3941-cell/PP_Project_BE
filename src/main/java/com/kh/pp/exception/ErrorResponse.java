package com.kh.pp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	private int code;
	private String message;
	private Object data;
}
