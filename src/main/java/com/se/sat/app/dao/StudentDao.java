package com.se.sat.app.dao;

import java.util.List;

import com.se.sat.app.entity.Student;

public interface StudentDao {
	
	Student findById(int id);
	void saveStudent(Student student);
	void updateStudent(Student student);
	void deleteById(int id);
	List<Student> findAllStudents();
	
}
