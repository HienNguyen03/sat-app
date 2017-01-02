package com.se.sat.app.service;

import java.util.List;

import com.se.sat.app.dto.CourseForm;
import com.se.sat.app.entity.Course;

public interface CourseService {

	public boolean addCourse(Integer id, CourseForm courseForm);
	
	public boolean update(Integer id, CourseForm editCourseForm);
	
	public boolean delete(Integer id);
	
	public List<Course> findCoursesByTeacher(Integer id);
	
	public Course findCourseInfo(Integer id);
	

}
