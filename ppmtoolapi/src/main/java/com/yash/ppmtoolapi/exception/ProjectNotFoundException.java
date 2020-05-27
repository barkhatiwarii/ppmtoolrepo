package com.yash.ppmtoolapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	
	// you are not forcing the code to run (whether error comes or not) here i.e why we are extending runtime 
	private static final long serialVersionUID = 1L;

	public ProjectNotFoundException(String errorMessage)
	{
		super(errorMessage);
	}
}
