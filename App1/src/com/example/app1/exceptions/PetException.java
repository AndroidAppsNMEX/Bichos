package com.example.app1.exceptions;

public class PetException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public PetException() {
		super();
	}
	
	public PetException(String msg) {
		super(msg);
	}
}