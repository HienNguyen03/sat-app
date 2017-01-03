package com.se.sat.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.service.TeacherService;
import com.se.sat.app.util.AppUtil;

@Controller
public class TeacherController {

	private static final Logger log = LoggerFactory.getLogger(TeacherController.class);

	private TeacherService teacherService;
	private CourseService courseService;

	@Autowired
	public TeacherController(TeacherService teacherService, CourseService courseService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
	}

	@RequestMapping(value = "/teacher")
	public String teacherHome(Model model) {
		
		Teacher teacher = AppUtil.getUserFromSession().getTeacher();
		
		List<Course> courses = courseService.findCoursesByTeacher(teacher);	
		
		model.addAttribute("courses", courses);
//		for(Course course : courses){
//			log.debug(course.toString());
//		}
		
		return "/teacher/home";
	}

}
