package com.se.sat.app.service;

import java.util.List;

import com.se.sat.app.dto.CourseForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Teacher;

public interface CourseService {

	public boolean insertCourse(CourseForm courseForm);
	public boolean updateCourse(Integer id, CourseForm editCourseForm);
	public boolean deleteCourse(Integer id);
	
	public List<Course> findCoursesByTeacher(Teacher teacher);
	public Course findCourseInfo(Integer id);
	public List<Course> findStudentCourseList();
	
}