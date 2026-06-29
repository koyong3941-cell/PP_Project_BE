package com.kh.pp.exception;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CustomAuthenticationException.class)
	public ResponseEntity<ErrorResponse> handlerAuthenticationError(CustomAuthenticationException e){
		return ResponseEntity.badRequest().body(new ErrorResponse(400, e.getMessage(), null));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handlerArgumentNoValid(MethodArgumentNotValidException e){
		
		List<FieldError> list = e.getBindingResult().getFieldErrors();
		Map<String, String> errors = new HashMap();
		
		e.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		
		return ResponseEntity.badRequest().body(new ErrorResponse(400, "유효하지 않은 요청", errors));	
	}
	
	@ExceptionHandler(DuplicateMemberException.class)
	public ResponseEntity<ErrorResponse> HandlerDuplicateId(DuplicateMemberException e) {
		ErrorResponse er = new ErrorResponse(400, e.getMessage(), null);
		
		return ResponseEntity.badRequest().body(er);
	}
	
	@ExceptionHandler(FailSignUpException.class)
	public ResponseEntity<ErrorResponse> HandlerFailSignUpId(FailSignUpException e){
		return ResponseEntity.internalServerError().body(new ErrorResponse(500, e.getMessage(), null));
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponse> HandlerUsernameNotFound(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "없는 자원입니다.", null));
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<ErrorResponse> HandlerInvalidParameter(InvalidParameterException e){
		return ResponseEntity.badRequest().body(new ErrorResponse(400, e.getMessage(), null));
	}
	
	@ExceptionHandler(FailDeleteException.class)
	public ResponseEntity<ErrorResponse> HandlerFailDelete(FailDeleteException e){
		return ResponseEntity.badRequest().body(new ErrorResponse(400, e.getMessage(), null));
	}
}
