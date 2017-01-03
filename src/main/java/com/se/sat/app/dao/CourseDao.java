package com.se.sat.app.dao;

import java.util.List;

import com.se.sat.app.entity.Course;

public interface CourseDao {
	
	Course findCourseById(int id);
	void saveCourse(Course course);
	void updateCourse(Course course);
	void deleteCourseById(int id);
	List<Course> findAllCourses();
	
}
