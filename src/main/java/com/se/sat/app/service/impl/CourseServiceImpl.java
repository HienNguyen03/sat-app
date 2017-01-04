package com.se.sat.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.dao.CourseDao;
import com.se.sat.app.dao.TeacherDao;
import com.se.sat.app.dto.CourseForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.util.AppUtil;

@Service("courseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseServiceImpl implements CourseService {

	private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

	private CourseDao courseDao;
	private TeacherDao teacherDao;

	@Autowired
	public CourseServiceImpl(CourseDao courseDao, TeacherDao teacherDao) {
		this.courseDao = courseDao;
		this.teacherDao = teacherDao;
	}

	@Override
	public boolean insertCourse(CourseForm courseForm) {
		Course course = new Course();

		course.setName(courseForm.getName());
		course.setDescription(courseForm.getDescription());
		course.setStartDate(courseForm.getStartDate());
		course.setEndDate(courseForm.getEndDate());
		course.setStartEnrollDate(courseForm.getStartEnrollDate());
		course.setEndEnrollDate(courseForm.getEndEnrollDate());
		course.setStatus("On-going");

		Teacher teacher = AppUtil.getUserFromSession().getTeacher();

		course.setTeacher(teacher);

		try {
			courseDao.insertCourse(course);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("Cannot save");
			return false;
		}

	}
	
	@Override
	public boolean updateCourse(Integer id, CourseForm editCourseForm) {

		Course course = courseDao.findCourseById(id);
		course.setName(editCourseForm.getName());
		course.setDescription(editCourseForm.getDescription());
		course.setStartDate(editCourseForm.getStartDate());
		course.setEndDate(editCourseForm.getEndDate());
		course.setStartEnrollDate(editCourseForm.getStartEnrollDate());
		course.setEndEnrollDate(editCourseForm.getEndEnrollDate());
		course.setStatus(editCourseForm.getStatus());

		try {
			courseDao.updateCourse(course);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Cannot update");
			return false;
		}
	}
	
	@Override
	public boolean deleteCourse(Integer id) {

		try {
			courseDao.deleteCourseById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Cannot delete");
			return false;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public List<Course> findCoursesByTeacher(Teacher teacher) {
		List<Course> courses = new ArrayList<Course>();
		courses = courseDao.findCoursesByTeacher(teacher);

		return courses;
	}

	@Override
	public Course findCourseInfo(Integer id) {
		Course course = courseDao.findCourseById(id);

		return course;
	}

	@Override
	public List<Course> findCoursesByStudent(Student student) {
		return courseDao.findCoursesByStudent(student);
	}

	
}