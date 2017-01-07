package com.se.sat.app.dto;

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Teacher;

public class CourseEnrollmentObjects {
	private String department;
	private Teacher teacher;
	private Course course;
	
	public CourseEnrollmentObjects(String department, Teacher teacher, Course course){
		this.department = department;
		this.teacher = teacher;
		this.course = course;
	}
	
	public CourseEnrollmentObjects(String department){
		this.department = department;
		this.teacher = null;
		this.course = null;
	}
	
	public CourseEnrollmentObjects(Teacher teacher){
		this.department = null;
		this.teacher = teacher;
		this.course = null;
	}
	
	public CourseEnrollmentObjects(Course course){
		this.department = null;
		this.teacher = null;
		this.course = course;
	}
	
	public CourseEnrollmentObjects(String department, Teacher teacher){
		this.department = department;
		this.teacher = teacher;
		this.course = null;
	}
	
	public CourseEnrollmentObjects(String department, Course course){
		this.department = department;
		this.teacher = null;
		this.course = course;
	}
	
	public CourseEnrollmentObjects(Teacher teacher, Course course){
		this.department = null;
		this.teacher = teacher;
		this.course = course;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	@Override
	public String toString() {
		return "CourseEnrollmentObjects [department=" + department + ", teacher=" + teacher + ", course=" + course + "]";
	}
	
}
