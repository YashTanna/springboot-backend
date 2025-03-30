package com.example.jwt_example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt_example.entities.Student;
import com.example.jwt_example.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentService service;
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id){
		Student student = service.getStudentById(id);
		return new ResponseEntity<Student>(student,HttpStatus.OK);
	}
	
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudent(){
		return new ResponseEntity<List<Student>>(service.getAllStudent(),HttpStatus.OK);
	}
	
	@PostMapping("/students")
	public ResponseEntity<Student> createStudent(@RequestBody Student student){
		return new ResponseEntity<Student>(service.createStudent(student),HttpStatus.OK);
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student,@PathVariable int id){
		return new ResponseEntity<Student>(service.updateStudent(id, student),HttpStatus.OK);
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable int id){
		service.deleteStudent(id);
		return new ResponseEntity<String>(String.format("Student with id : %d deleted Successfully...", id),HttpStatus.OK);
	}
	
}














