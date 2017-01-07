package com.se.sat.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.Teacher;

public interface CourseDao {

	void insertCourse(Course course);
	void updateCourse(Course course);
	void deleteCourseById(int id);

	Course findCourseById(int id);
	List<Course> findCoursesByTeacher(Teacher teacher);
	List<Course> findCoursesByStudent(Student student);
	List<Course> findAllCourses();

	List<Object[]> getGroupsOfCoursesByTeachers(Student student);
}
