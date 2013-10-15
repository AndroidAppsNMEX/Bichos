package com.example.app1.exceptions;

public class OwnerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OwnerException() {
		super();
	}

	public OwnerException(String msg) {
		super(msg);
	}
}