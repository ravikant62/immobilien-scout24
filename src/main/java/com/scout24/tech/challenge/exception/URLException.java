package com.scout24.tech.challenge.exception;

public class URLException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private Integer errorCode;
	private String url;
	private String message;

	public URLException(String url, Integer errorCode, String errorMessage ) {
		this.message	=	 errorMessage;
		this.url = url;
		this.errorCode = errorCode;
	}
	
	public URLException(String url, String errorMessage ) {
		super(errorMessage);
		this.url = url;
	}

	public URLException( String errorMessage ) {
		super(errorMessage);
	}

	public URLException( Exception e ) {
		super(e);
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public String getUrl() {
		return url;
	}
	public String getMessage() {
		return message;
	}
}
