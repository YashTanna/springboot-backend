package com.example.jwt_example.service;

import java.util.List;

import com.example.jwt_example.entities.Student;

public interface StudentService {
	
	Student createStudent(Student student);
	
	Student updateStudent(int id,Student student);
	
	Student getStudentById(int id);
	
	List<Student> getAllStudent();
	
	void deleteStudent(int id);
}
