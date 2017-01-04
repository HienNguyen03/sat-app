package com.se.sat.app.controller;

import java.util.Date;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.service.StudySessionService;
import com.se.sat.app.util.AppUtil;
import com.se.sat.app.validator.CustomDateTimeFormat;

//@Controller
public class StudySessionController {

//	private static final Logger log = LoggerFactory.getLogger(StudySessionController.class);
//
//	private CourseService courseService;
//	private StudySessionService studySessionService;
//
//	@Autowired
//	public StudySessionController(CourseService courseService, StudySessionService studySessionService) {
//		this.courseService = courseService;
//		this.studySessionService = studySessionService;
//	}
//
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new CustomDateTimeFormat(), true));
//	}
//
//	@RequestMapping(value = "/teacher/course/{courseId}")
//	public String coursePage(@PathVariable("courseId") Integer courseId, Model model) {
//
//		Course course = courseService.findCourseInfo(courseId);
//
//		model.addAttribute("course", course);
//
//		return "/teacher/course-page";
//	}
//
//	@RequestMapping(value = "/teacher/course/{courseId}/studySession", method = RequestMethod.GET)
//	public String addSession(@PathVariable("courseId") Integer courseId, Model model) {
//
//		StudySessionForm studySessionForm = new StudySessionForm();
//		model.addAttribute("studySessionForm", studySessionForm);
//
//		return "/teacher/study-session";
//	}
//
//	@RequestMapping(value = "/teacher/course/{courseId}/studySession", method = RequestMethod.POST)
//	public String addSession(@PathVariable("courseId") Integer courseId, Model model,
//			@ModelAttribute("studySessionForm") @Valid StudySessionForm studySessionForm, BindingResult result,
//			RedirectAttributes redirectAttributes) throws ServletException {
//
//		if (result.hasErrors()) {
//			// log.info(result.getAllErrors().toString());
//			model.addAttribute("studySessionForm", studySessionForm);
//
//			return "/teacher/study-session";
//		}
//
//		boolean saveResult = studySessionService.insertStudySession(courseId, studySessionForm);
//
//		if (saveResult)
//			AppUtil.flash(redirectAttributes, "success", "studySession.success");
//		else
//			AppUtil.flash(redirectAttributes, "danger", "studySession.failure");
//
//		return "redirect:/teacher/course/{courseId}";
//	}

}
