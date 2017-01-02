package com.se.sat.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.sat.app.dto.CourseForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.service.TeacherService;
import com.se.sat.app.util.AppUtil;

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

@Controller
public class CourseController {
	private static final Logger log = LoggerFactory.getLogger(CourseController.class);

	private TeacherService teacherService;
	private CourseService courseService;

	@Autowired
	public CourseController(TeacherService teacherService, CourseService courseService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
	}

	@RequestMapping(value = "/teacher/{id}/course", method = RequestMethod.GET)
	public String addCourse(@PathVariable("id") Integer id, Model model) {

		CourseForm courseForm = new CourseForm();
		model.addAttribute("courseForm", courseForm);

		return "/teacher/course";
	}

	@RequestMapping(value = "/teacher/{id}/course", method = RequestMethod.POST)
	public String addCourse(@PathVariable("id") Integer id, Model model,
			@ModelAttribute("courseForm") @Valid CourseForm courseForm, BindingResult result,
			RedirectAttributes redirectAttributes, SessionStatus status, HttpServletRequest request)
			throws ServletException {

		if (result.hasErrors()) {
			log.info(result.getAllErrors().toString());
			model.addAttribute("courseForm", courseForm);

			return "/teacher/course";
		}

		boolean saveResult = courseService.addCourse(id, courseForm);

		log.debug(courseForm.toString());

		if (saveResult)
			AppUtil.flash(redirectAttributes, "success", "course.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "course.failure");

		status.setComplete();
		return "redirect:/teacher/{id}";
	}

	@RequestMapping(value = "/teacher/{id}/course/{courseId}/edit", method = RequestMethod.GET)
	public String editCourse(@PathVariable("id") Integer id, @PathVariable("courseId") Integer courseId, Model model) {

		Course course = courseService.findCourseInfo(courseId);
		CourseForm editCourseForm = new CourseForm();
		editCourseForm.setName(course.getName());
		editCourseForm.setDescription(course.getDescription());
		editCourseForm.setStartDate(course.getStartDate());
		editCourseForm.setEndDate(course.getEndDate());
		editCourseForm.setStartEnrollDate(course.getStartEnrollDate());
		editCourseForm.setEndEnrollDate(course.getEndEnrollDate());
		editCourseForm.setStatus(course.getStatus());

		model.addAttribute("editCourseForm", editCourseForm);

		return "/teacher/edit-course";
	}

	@RequestMapping(value = "/teacher/{id}/course/{courseId}/edit", method = RequestMethod.POST)
	public String editCourse(@PathVariable("id") Integer id, @PathVariable("courseId") Integer courseId, Model model,
			@ModelAttribute @Valid CourseForm editCourseForm, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws ServletException {

		if (result.hasErrors()) {
			log.info(result.getAllErrors().toString());
			model.addAttribute("editCourseForm", editCourseForm);

			return "/teacher/edit-course";
		}

		boolean updateResult = courseService.update(courseId, editCourseForm);

		if (updateResult)
			AppUtil.flash(redirectAttributes, "success", "course.success");

		else
			AppUtil.flash(redirectAttributes, "danger", "course.failure");

		return "redirect:/teacher/{id}";
	}

	@RequestMapping(value = "/teacher/{id}/course/{courseId}/delete")
	public String deleteCourse(@PathVariable("id") Integer id, @PathVariable("courseId") Integer courseId, final RedirectAttributes redirectAttributes) {
		
		boolean deleteResult = courseService.delete(courseId);
		
		if (deleteResult)
			AppUtil.flash(redirectAttributes, "success", "course.deleteSuccess");

		else
			AppUtil.flash(redirectAttributes, "danger", "course.deleteFailure");
		
		return "redirect:/teacher/{id}";

	}

}
