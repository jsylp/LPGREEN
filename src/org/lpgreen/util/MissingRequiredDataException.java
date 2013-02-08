package org.lpgreen.util;

public class MissingRequiredDataException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MissingRequiredDataException() {}
	
	public MissingRequiredDataException(String msg) {
		super(msg);
	}
}
