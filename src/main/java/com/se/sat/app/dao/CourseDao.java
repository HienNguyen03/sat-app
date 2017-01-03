package com.se.sat.app.dao;

import java.util.List;

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Teacher;

public interface CourseDao {

	void insertCourse(Course course);
	void updateCourse(Course course);
	void deleteCourseById(int id);

	Course findCourseById(int id);
	List<Course> findCoursesByTeacher(Teacher teacher);
	List<Course> findAllCourses();
	
}
