package com.ensolvers.demo.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 543149453265360943L;

	public ResourceNotFoundException() {
        super("Resource Not Found");
    }
}
