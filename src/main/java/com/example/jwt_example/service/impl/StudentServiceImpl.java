package com.example.jwt_example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt_example.entities.Student;
import com.example.jwt_example.exception.StudentNotFoundException;
import com.example.jwt_example.repository.StudentRepository;
import com.example.jwt_example.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository repo;

	@Override
	public Student createStudent(Student student) {
		Student savedStudent = repo.save(student);
		return savedStudent;
	}

	@Override
	public Student updateStudent(int id, Student student) {
		Student stud = repo.findById(id).orElseThrow(()->new StudentNotFoundException(id));
		
		student.setId(stud.getId());
		Student updatedStudent = repo.save(student);
		
		return updatedStudent;
	}

	@Override
	public Student getStudentById(int id) {
		Student student = repo.findById(id).orElseThrow(()->new StudentNotFoundException(id));
		return student;
	}

	@Override
	public List<Student> getAllStudent() {
		return repo.findAll();
	}

	@Override
	public void deleteStudent(int id) {
		repo.deleteById(id);
	}

}
