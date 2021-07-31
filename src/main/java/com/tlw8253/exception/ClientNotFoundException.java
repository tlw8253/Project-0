package com.tlw8253.exception;

public class ClientNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientNotFoundException() {
	}

	public ClientNotFoundException(String message) {
		super(message);
	}

	public ClientNotFoundException(Throwable cause) {
		super(cause);
	}

	public ClientNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
