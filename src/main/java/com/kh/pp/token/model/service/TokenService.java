package com.kh.pp.token.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.exception.CustomAuthenticationException;
import com.kh.pp.token.model.dao.TokenMapper;
import com.kh.pp.token.model.vo.RefreshToken;
import com.kh.pp.token.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtUtil tokenUtil;
	private final TokenMapper tokenMapper;
	
	public Map<String, String> getTokens(CustomUserDetails user) {
		Map<String, String> tokens = createTokens(user);
		saveToken(tokens.get("refreshToken"),user.getUsername());
		
		return tokens;
	}
	
	// 토큰 생성 및 반환 메소드
	private Map<String,String> createTokens(CustomUserDetails user) {
		return Map.of("accessToken", tokenUtil.getAccessToken(user),
						"refreshToken", tokenUtil.getRefreshToken(user));
	}
	
	// 리프레시토큰을 받아서 DB에 INSERT 메소드
	private void saveToken(String token, String adminId) {
		RefreshToken refreshToken = RefreshToken.builder()
										.adminId(adminId)
										.token(token)
										.expiration(System.currentTimeMillis()+ (1000*60*60*24*3))
										.build();
		tokenMapper.saveToken(refreshToken);
	}
	// 로그아웃 요청 시 DB정리 메서드
	public void logout(String memberId) {
		tokenMapper.deleteToken(memberId);
	}
	// 추후 AccessToken이 만료기간이 지나서 토큰 갱신 요청이 들어왔을때
	// 사용자에게 전달받은 RefreshToken이 DB에 존재하면서 만료기간이 지나지 않았는지 검증
	
	public Map<String, String> tokenLocation(String refreshToken){
		RefreshToken token = tokenMapper.findByToken(refreshToken);
		if(token == null || token.getExpiration() < System.currentTimeMillis()) {
			throw new CustomAuthenticationException("유효하지 않은 토큰입니다.");
		}
		Claims claims = tokenUtil.parseJwt(token.getToken());
		String memberId = claims.getSubject();
		
		String memberName = (String)claims.get("memberName");
		CustomUserDetails user = CustomUserDetails.builder().memberName(memberName).username(memberId).build();
		return createTokens(user);
	}
	
	
	
	
}
