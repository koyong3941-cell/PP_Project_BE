package com.kh.pp.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.auth.model.dto.LoginRequestDto;
import com.kh.pp.auth.model.dto.LoginResponse;
import com.kh.pp.auth.model.service.AuthService;
import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.common.api.ApiResponse;
import com.kh.pp.token.model.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequestDto lrd){
		LoginResponse res = authService.login(lrd);
		
		return ResponseEntity.ok(ApiResponse.success(res));
	}
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal CustomUserDetails user){
		tokenService.logout(user.getMemberNo());
		
		return ResponseEntity.status(204).body(ApiResponse.noContent("로그아웃 성공", null));
	}
	
}



/*
JwtUtil (토큰 생성/검증)
- TokenService > 토큰 발급
- TokenMapper > 토큰 찍는 곳
- AuthService > 로그인 인증
- AdminMapper > 아이디 찍는 곳
- AuthController > 로그인 데이터 컨트롤
	 
	 
	 
implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
implementation 'io.jsonwebtoken:jjwt-jackson:0.12.3'
implementation 'io.jsonwebtoken:jjwt-impl:0.12.3'

준비작업: memberVO, memberDto, LoginRequestDto, LoginResponse
1. SecurityConfiguration, JwtFilter (필터 및 접근 권한 설정)
2. PasswordEncoder > BCryptPasswordEncoder 객체 생성 > CORS인증 모듈 
3. CustomUserDetails & UserDetailsService (시큐리티 회원 정보 연결)
4. JwtUtil (토큰 발급/검증 유틸) AuthController, AuthService, TokenService, TokenMapper
5. JwtAuthenticationFilter (모든 요청 토큰 체크)
6. ExceptionHandler ex: FailSingUpException
7. memberController > memberService ...

준비작업
- MemberVO(Entity)
- MemberDto
- LoginRequestDto
- LoginResponseDto

1. SecurityConfiguration
- SecurityFilterChain
- CORS 설정
- 접근 권한 설정
- PasswordEncoder Bean 등록

2. CustomUserDetails
- UserDetails 구현

3. CustomUserDetailsService
- UserDetailsService 구현
- loadUserByUsername()

4. JWT 인증 모듈
- JwtUtil (토큰 생성/검증)
- TokenService 
- TokenMapper
- AuthService 
- AuthController

5. JwtAuthenticationFilter
- 모든 요청 JWT 검증
- SecurityContext 등록

6. 예외 처리
- GlobalExceptionHandler
- FailSignUpException
- LoginFailException
- JwtException

7. 회원 기능 구현
- MemberController
- MemberService
- MemberMapper
	 */