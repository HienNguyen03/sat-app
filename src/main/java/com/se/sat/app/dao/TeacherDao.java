package com.se.sat.app.dao;

import java.util.List;

import com.se.sat.app.entity.Teacher;

public interface TeacherDao {
	
	Teacher findById(int id);
	void saveTeacher(Teacher teacher);
	void updateTeacher(Teacher teacher);
	void deleteById(int id);
	List<Teacher> findAllTeachers();
	
}
