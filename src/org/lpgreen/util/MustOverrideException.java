package org.lpgreen.util;

public class MustOverrideException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MustOverrideException() {}
	
	public MustOverrideException(String msg) {
		super(msg);
	}
}
