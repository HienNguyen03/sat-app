package com.se.sat.app.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.sat.app.dto.CourseForm;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.service.TeacherService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class CourseController {
	private static final Logger log = LoggerFactory.getLogger(CourseController.class);

	private TeacherService teacherService;

	public CourseController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@RequestMapping(value = "/{teacherId}/course}", method = RequestMethod.GET)
	public String addCourse(@PathVariable Integer teacherId, Model model) {
		Teacher teacher = teacherService.findTeacherInfo(teacherId);
		CourseForm courseForm = new CourseForm();

		model.addAttribute(courseForm);

		return "/{teacherId}/course";
	}

	@RequestMapping(value = "/{teacherId}/course}", method = RequestMethod.POST)
	public String addCourse(@PathVariable Integer teacherId, Model model,@ModelAttribute @Valid CourseForm courseForm,
			BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()){
			log.info(result.getAllErrors().toString());
			model.addAttribute("courseForm", courseForm);
			return "/teacher/{teacherId}/course";
		}
		
		return "redirect:/{teacherId}";
	}

}
