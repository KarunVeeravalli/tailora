package com.tailora.exception;

public class UnAuthUserException extends Exception{

	private static final long serialVersionUID = -3591408998487163063L;

	public UnAuthUserException() {
		super();
	}
	
	public UnAuthUserException(String msg) {
		super(msg);
	}
}
