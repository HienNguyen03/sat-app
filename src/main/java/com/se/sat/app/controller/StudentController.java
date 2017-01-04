package com.se.sat.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.User;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.util.AppUtil;

@Controller
@RequestMapping("/student")
public class StudentController {

	private static final Logger log = LoggerFactory.getLogger(StudentController.class);

	private CourseService courseService;

	@Autowired
	public StudentController(CourseService courseService) {
		this.courseService = courseService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		User user = AppUtil.getUserFromSession();
		List<Course> courses = courseService.findCoursesByStudent(user.getStudent());
		model.addAttribute("courses", courses);
		return "student/home";
	}

	@RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
	public String viewCourse(Model model, @PathVariable Integer id) {
		Course course = courseService.findCourseInfo(id);
		model.addAttribute("course", course);
		return "student/course";
	}

}
