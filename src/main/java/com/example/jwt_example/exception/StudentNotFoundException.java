package com.example.jwt_example.exception;

public class StudentNotFoundException extends RuntimeException{
	
	public StudentNotFoundException(int id) {
		super(String.format("Student with id %d was not found",id));
	}
	
	public StudentNotFoundException(String username) {
		super(String.format("User not found with username : %s", username));
	}

}
