package com.example.jwt_example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt_example.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
