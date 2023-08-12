package com.example.springBoot_hibernate_ferrocarriles.exception;

public class NoDataException extends RuntimeException{
	
	//private static final long serialVersionUID = 2L;

	public NoDataException(String msg) {
		super(msg);		
	}

}
