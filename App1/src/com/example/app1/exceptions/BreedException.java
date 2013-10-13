package com.example.app1.exceptions;

public class BreedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BreedException(){
		super();
	}
	
	public BreedException(String msg){
		super(msg);
	}
}
