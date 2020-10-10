package me.shw.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status code
// 2XX  -> OK
// 4XX  -> Client
// 5XX  -> Server

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7809907983469625960L;

	public UserNotFoundException(String message) {
	
		super(message);
	}
	
}
