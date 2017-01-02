package com.se.sat.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.dto.CourseForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.repository.CourseRepo;
import com.se.sat.app.repository.TeacherRepo;
import com.se.sat.app.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

	private CourseRepo courseRepo;
	private TeacherRepo teacherRepo;

	@Autowired
	public CourseServiceImpl(CourseRepo courseRepo, TeacherRepo teacherRepo) {
		this.courseRepo = courseRepo;
		this.teacherRepo = teacherRepo;

	}

	@Override
	public boolean addCourse(Integer id, CourseForm courseForm) {
		Course course = new Course();

		course.setName(courseForm.getName());
		course.setDescription(courseForm.getDescription());
		course.setStartDate(courseForm.getStartDate());
		course.setEndDate(courseForm.getEndDate());
		course.setStartEnrollDate(courseForm.getStartEnrollDate());
		course.setEndEnrollDate(courseForm.getEndEnrollDate());
		course.setStatus("On-going");

		Teacher teacher = teacherRepo.findTeacher(id);
		course.setTeacher(teacher);

		try {
			courseRepo.insert(course);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Cannot save");
			return false;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public List<Course> findCoursesByTeacher(Integer id) {
		Teacher teacher = teacherRepo.findTeacher(id);
		List<Course> courses = new ArrayList<Course>();
		courses = courseRepo.findCourseByTeacher(teacher);

		return courses;
	}

	@Override
	public Course findCourseInfo(Integer id) {
		Course course = courseRepo.findCourse(id);

		return course;
	}

	@Override
	public boolean update(Integer id, CourseForm editCourseForm) {

		Course course = courseRepo.findCourse(id);
		course.setName(editCourseForm.getName());
		course.setDescription(editCourseForm.getDescription());
		course.setStartDate(editCourseForm.getStartDate());
		course.setEndDate(editCourseForm.getEndDate());
		course.setStartEnrollDate(editCourseForm.getStartEnrollDate());
		course.setEndEnrollDate(editCourseForm.getEndEnrollDate());
		course.setStatus(editCourseForm.getStatus());

		try {
			courseRepo.saveOrUpdate(course);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Cannot update");
			return false;
		}
	}

	@Override
	public boolean delete(Integer id) {

		Course course = courseRepo.findCourse(id);

		try {
			courseRepo.delete(course);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Cannot delete");
			return false;
		}
	}

}
