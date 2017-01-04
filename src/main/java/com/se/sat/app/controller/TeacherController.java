package com.se.sat.app.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

import com.se.sat.app.dto.CourseForm;
import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.service.StudySessionService;
import com.se.sat.app.service.TeacherService;
import com.se.sat.app.util.AppUtil;
import com.se.sat.app.validator.CustomDateTimeFormat;

@Controller
public class TeacherController {

	private static final Logger log = LoggerFactory.getLogger(TeacherController.class);

	private TeacherService teacherService;
	private CourseService courseService;
	private StudySessionService studySessionService;

	@Autowired
	public TeacherController(TeacherService teacherService, CourseService courseService,
			StudySessionService studySessionService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
		this.studySessionService = studySessionService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "startTime", new CustomDateEditor(new SimpleDateFormat("HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, "endTime", new CustomDateEditor(new SimpleDateFormat("HH:mm:ss"), true));
	}
	
	
	/**
	 * 
	 * TEACHER
	 * 
	 * 
	 * */

	@RequestMapping(value = "/teacher")
	public String teacherHome(Model model) {

		Teacher teacher = AppUtil.getUserFromSession().getTeacher();

		List<Course> courses = courseService.findCoursesByTeacher(teacher);

		model.addAttribute("courses", courses);
		// for(Course course : courses){
		// log.debug(course.toString());
		// }

		return "/teacher/home";
	}
	
	
	
	
	/**
	 * 
	 * COURSE
	 * 
	 * 
	 * */
	
	@RequestMapping(value = "/teacher/course", method = RequestMethod.GET)
	public String addCourse(Model model) {

		CourseForm courseForm = new CourseForm();
		model.addAttribute("courseForm", courseForm);

		return "/teacher/course";
	}

	@RequestMapping(value = "/teacher/course", method = RequestMethod.POST)
	public String addCourse(Model model, @ModelAttribute("courseForm") @Valid CourseForm courseForm,
			BindingResult result, RedirectAttributes redirectAttributes)
			throws ServletException {

		if (result.hasErrors()) {
			//log.info(result.getAllErrors().toString());
			model.addAttribute("courseForm", courseForm);

			return "/teacher/course";
		}

		boolean saveResult = courseService.insertCourse(courseForm);
	
		if (saveResult)
			AppUtil.flash(redirectAttributes, "success", "course.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "course.failure");

		return "redirect:/teacher";
	}

	@RequestMapping(value = "/teacher/course/{courseId}/edit", method = RequestMethod.GET)
	public String editCourse(@PathVariable("courseId") Integer courseId, Model model) {

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

	@RequestMapping(value = "/teacher/course/{courseId}/edit", method = RequestMethod.POST)
	public String editCourse(@PathVariable("courseId") Integer courseId, Model model,
			@ModelAttribute @Valid CourseForm editCourseForm, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws ServletException {

		if (result.hasErrors()) {
			//log.info(result.getAllErrors().toString());
			model.addAttribute("editCourseForm", editCourseForm);

			return "/teacher/edit-course";
		}

		boolean updateResult = courseService.updateCourse(courseId, editCourseForm);

		if (updateResult)
			AppUtil.flash(redirectAttributes, "success", "course.success");

		else
			AppUtil.flash(redirectAttributes, "danger", "course.failure");

		return "redirect:/teacher";
	}

	@RequestMapping(value = "/teacher/course/{courseId}/delete")
	public String deleteCourse(@PathVariable("courseId") Integer courseId,
			final RedirectAttributes redirectAttributes) {

		boolean deleteResult = courseService.deleteCourse(courseId);

		if (deleteResult)
			AppUtil.flash(redirectAttributes, "success", "course.deleteSuccess");

		else
			AppUtil.flash(redirectAttributes, "danger", "course.deleteFailure");

		return "redirect:/teacher";

	}
	
	
	
	
	/**
	 * 
	 * STUDY SESSION
	 * 
	 * 
	 * */
	@RequestMapping(value = "/teacher/course/{courseId}")
	public String coursePage(@PathVariable("courseId") Integer courseId, Model model) {

		Course course = courseService.findCourseInfo(courseId);

		model.addAttribute("course", course);

		return "/teacher/course-page";
	}

	@RequestMapping(value = "/teacher/course/{courseId}/studySession", method = RequestMethod.GET)
	public String addSession(@PathVariable("courseId") Integer courseId, Model model) {

		StudySessionForm studySessionForm = new StudySessionForm();
		model.addAttribute("studySessionForm", studySessionForm);

		return "/teacher/study-session";
	}

	@RequestMapping(value = "/teacher/course/{courseId}/studySession", method = RequestMethod.POST)
	public String addSession(@PathVariable("courseId") Integer courseId, Model model,
			@ModelAttribute("studySessionForm") @Valid StudySessionForm studySessionForm, BindingResult result,
			RedirectAttributes redirectAttributes) throws ServletException {

		if (result.hasErrors()) {
			// log.info(result.getAllErrors().toString());
			model.addAttribute("studySessionForm", studySessionForm);

			return "/teacher/study-session";
		}

		boolean saveResult = studySessionService.insertStudySession(courseId, studySessionForm);

		if (saveResult)
			AppUtil.flash(redirectAttributes, "success", "studySession.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "studySession.failure");

		return "redirect:/teacher/course/{courseId}";
	}
}
