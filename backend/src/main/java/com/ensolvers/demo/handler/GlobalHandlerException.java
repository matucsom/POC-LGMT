package com.ensolvers.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ensolvers.demo.exception.ApiError;
import com.ensolvers.demo.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalHandlerException {

	 @ExceptionHandler(ResourceNotFoundException.class)
	 public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
	        
	        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	    }
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
	        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), "An unexpected error occurred");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
	    }
}
