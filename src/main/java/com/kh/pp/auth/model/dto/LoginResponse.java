package com.kh.pp.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
	private Integer memberNo;
	private String memberId;
	private String role;
	private String accessToken;
	private String refreshToken;
}
