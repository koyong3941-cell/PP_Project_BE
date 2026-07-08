package com.kh.pp.exception;

/**
 * 액세스 토큰의 memberNo와 URL PathVariable의 memberNo가 일치하지 않을 때 발생.
 * 기존 공용 예외 핸들러(@ControllerAdvice)에 아래와 같이 401 응답으로 매핑 필요:
 *
 * {
 *   "code": 401,
 *   "msg": "잘못된 회원정보",
 *   "errors": { "message": "로그인 상태가 불안정합니다. 다시 로그인 해주세요." }
 * }
 */
public class InvalidMemberInfoException extends RuntimeException {
	public InvalidMemberInfoException(String message) {
		super(message);
	}
}
