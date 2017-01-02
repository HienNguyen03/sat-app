package com.se.sat.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.service.CourseService;

@Controller
public class StudySessionController {

	private static final Logger log = LoggerFactory.getLogger(StudySessionController.class);

	private CourseService courseService;

	@Autowired
	public StudySessionController(CourseService courseService) {
		this.courseService = courseService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
	}

	@RequestMapping(value = "/teacher/{id}/course/{courseId}")
	public String coursePage(@PathVariable("courseId") Integer courseId, @PathVariable("id") Integer id, Model model) {

		Course course = courseService.findCourseInfo(courseId);

		model.addAttribute("course", course);

		return "/teacher/coursePage";
	}

	@RequestMapping(value = "/teacher/{id}/course/{courseId}/studySession", method = RequestMethod.GET)
	public String addSession(@PathVariable("id") Integer id, @PathVariable("courseId") Integer courseId, Model model) {

		StudySessionForm studySessionForm = new StudySessionForm();
		model.addAttribute("studySessionForm", studySessionForm);

		return "/teacher/studySession";
	}

}
