package com.example.jwt_example.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<Map<String, String>> studentNotFoundException(StudentNotFoundException ex){
		String message = ex.getMessage();
		
		Map<String, String> res = new HashMap<String, String>();
		res.put("error", message);
		
		return new ResponseEntity<Map<String,String>>(res,HttpStatus.NOT_FOUND);
	}
	
}
