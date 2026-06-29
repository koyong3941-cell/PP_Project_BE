package com.kh.pp.token.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {
	private Integer memberNo;
	private String memberId;
	private String token;
	private Long expirationToken;
}
