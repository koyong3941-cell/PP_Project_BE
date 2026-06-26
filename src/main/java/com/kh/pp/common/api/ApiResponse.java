package com.kh.pp.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
	private int code;
	private String message;
	private T data;
	
		// 200 성공, 응답
		public static <T> ApiResponse<T> success(T data){
			return new ApiResponse<>(200, "요청에 성공하였습니다.", data);
		}
		
		// 201 성공 응답
		public static <T> ApiResponse<T> success(String message, T data){
			return new ApiResponse<>(200, message, data);
		}
		
		// 201 성공 응답
		
		public static <T> ApiResponse<T> created(T data){
			return new ApiResponse<>(201, "요청에 성공하였습니다.", data);
		}
		
		// 201 성공 응답
		public static <T> ApiResponse<T> created(String message, T data){
			return new ApiResponse<>(201, message, data);
		}
		
		//204 성공 응답
		public static <T> ApiResponse<T> noContent(String message, T data){
			return new ApiResponse<>(204, message, data);
		}

}
