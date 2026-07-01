package com.kh.pp.exception;

public class FailUserRequestException extends RuntimeException{
	public FailUserRequestException(String message){
		super(message);
	}
}
