package br.com.rperatello.bankcoreapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidRequestException() {
		super("Request not acepted");
	}	
	
	public InvalidRequestException(String ex) {
		super(ex);
	}

}