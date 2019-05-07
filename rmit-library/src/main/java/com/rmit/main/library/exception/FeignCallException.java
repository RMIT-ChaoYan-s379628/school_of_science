package com.rmit.main.library.exception;

public class FeignCallException extends Exception{

	private static final long serialVersionUID = 6330927175800105482L;
	
	public FeignCallException(String message) {
		super(message);
	}

}
