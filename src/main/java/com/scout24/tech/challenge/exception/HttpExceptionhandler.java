package com.scout24.tech.challenge.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Controller
@ControllerAdvice
public class HttpExceptionhandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler({URLException.class})
	public  ResponseEntity<Object> handleUnknownUserException(URLException e, WebRequest request ) { 
		if( e.getErrorCode() == null ){
			e = new URLException(e.getUrl(),500,e.getMessage());
		}
		
		HttpStatus.valueOf(e.getErrorCode());
		
		return super.handleExceptionInternal( e, 
				new HttpErrorResponseBody( e.getUrl(), e.getErrorCode(), e.getMessage() ), 
				new HttpHeaders(),
				HttpStatus.valueOf(e.getErrorCode()),
				request );
    }
	
	
	public class HttpErrorResponseBody {
		
		public String url ;
		public Integer errorCode;
		public String message;
		
		public HttpErrorResponseBody( String url, Integer errorCode, String message) {
			this.url		= url;
			this.errorCode	= errorCode;
			this.message	=	message;
		}
	}

}
