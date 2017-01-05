package com.se.sat.app.controller;

import java.text.DateFormat;
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
import com.se.sat.app.dto.EditStudySessionForm;
import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.dto.StudySessionPasswordForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.service.StudySessionService;
import com.se.sat.app.service.TeacherService;
import com.se.sat.app.util.AppUtil;

@Controller
@RequestMapping("/teacher")
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
		binder.registerCustomEditor(Date.class, "startTime",
				new CustomDateEditor(new SimpleDateFormat("HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, "endTime",
				new CustomDateEditor(new SimpleDateFormat("HH:mm:ss"), true));
	}

	/**
	 * 
	 * TEACHER
	 * 
	 * 
	 */

	@RequestMapping(method = RequestMethod.GET)
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
	 */

	@RequestMapping(value = "/new-course", method = RequestMethod.GET)
	public String addCourse(Model model) {

		CourseForm courseForm = new CourseForm();
		model.addAttribute("courseForm", courseForm);

		return "/teacher/new-course";
	}

	@RequestMapping(value = "/new-course", method = RequestMethod.POST)
	public String addCourse(Model model, @ModelAttribute("courseForm") @Valid CourseForm courseForm,
			BindingResult result, RedirectAttributes redirectAttributes) throws ServletException {

		if (result.hasErrors()) {
			// log.info(result.getAllErrors().toString());
			model.addAttribute("courseForm", courseForm);

			return "/teacher/new-course";
		}

		boolean saveResult = courseService.insertCourse(courseForm);

		if (saveResult)
			AppUtil.flash(redirectAttributes, "success", "course.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "course.failure");

		return "redirect:/teacher";
	}

	@RequestMapping(value = "/course/{courseId}/edit", method = RequestMethod.GET)
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

	@RequestMapping(value = "/course/{courseId}/edit", method = RequestMethod.POST)
	public String editCourse(@PathVariable("courseId") Integer courseId, Model model,
			@ModelAttribute @Valid CourseForm editCourseForm, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws ServletException {

		if (result.hasErrors()) {
			// log.info(result.getAllErrors().toString());
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

	@RequestMapping(value = "/course/{courseId}/delete")
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
	 */
	@RequestMapping(value = "/course/{courseId}")
	public String coursePage(@PathVariable("courseId") Integer courseId, Model model) {

		Course course = courseService.findCourseInfo(courseId);
		List<StudySession> studySessions = studySessionService.findStudySessionByCourse(courseId);

		model.addAttribute("studySessions", studySessions);
		model.addAttribute("course", course);

		return "/teacher/course-page";
	}

	@RequestMapping(value = "/course/{courseId}/new-study-session", method = RequestMethod.GET)
	public String addSession(@PathVariable("courseId") Integer courseId, Model model) {

		StudySessionForm studySessionForm = new StudySessionForm();
		model.addAttribute("studySessionForm", studySessionForm);

		return "/teacher/new-study-session";
	}

	@RequestMapping(value = "/course/{courseId}/new-study-session", method = RequestMethod.POST)
	public String addSession(@PathVariable("courseId") Integer courseId, Model model,
			@ModelAttribute("studySessionForm") @Valid StudySessionForm studySessionForm, BindingResult result,
			RedirectAttributes redirectAttributes) throws ServletException {

		if (result.hasErrors()) {
			// log.info(result.getAllErrors().toString());
			model.addAttribute("studySessionForm", studySessionForm);

			return "/teacher/new-study-session";
		}

		boolean saveResult = studySessionService.insertStudySession(courseId, studySessionForm);

		if (saveResult)
			AppUtil.flash(redirectAttributes, "success", "studySession.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "studySession.failure");

		return "redirect:/teacher/course/{courseId}";
	}

	@RequestMapping(value = "/course/{courseId}/study-session/{studySessionId}/edit", method = RequestMethod.GET)
	public String editStudySession(@PathVariable("courseId") Integer courseId,
			@PathVariable("studySessionId") Integer studySessionId, Model model) {

		StudySession studySession = studySessionService.findStudySessionInfo(studySessionId);
		// log.debug(studySession.toString());
		EditStudySessionForm editStudySessionForm = new EditStudySessionForm();

		editStudySessionForm.setName(studySession.getName());
		editStudySessionForm.setStartTime(studySession.getStartTime());
		editStudySessionForm.setEndTime(studySession.getEndTime());
		editStudySessionForm.setSessionDate(studySession.getSessionDate());
		editStudySessionForm.setSessionCategory(studySession.getSessionCategory());

		// log.debug(editStudySessionForm.toString());

		model.addAttribute("editStudySessionForm", editStudySessionForm);

		return "/teacher/edit-study-session";
	}

	@RequestMapping(value = "/course/{courseId}/study-session/{studySessionId}/edit", method = RequestMethod.POST)
	public String editStudySession(@PathVariable("courseId") Integer courseId,
			@PathVariable("studySessionId") Integer studySessionId, Model model,
			@ModelAttribute("editStudySessionForm") @Valid EditStudySessionForm editStudySessionForm,
			BindingResult result, RedirectAttributes redirectAttributes) throws ServletException {

		if (result.hasErrors()) {
			model.addAttribute("editStudySessionForm", editStudySessionForm);
			// log.debug(editStudySessionForm.toString());
			return "/teacher/edit-study-session";
		}

		boolean updateInfoResult = studySessionService.updateStudySessionInfo(studySessionId, editStudySessionForm);

		if (updateInfoResult)
			AppUtil.flash(redirectAttributes, "success", "studySession.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "studySession.failure");

		return "redirect:/teacher/course/{courseId}";

	}

	@RequestMapping(value = "/course/{courseId}/study-session/{studySessionId}/change-password", method = RequestMethod.GET)
	public String changeStudySessionPassword(@PathVariable("courseId") Integer courseId,
			@PathVariable("studySessionId") Integer studySessionId, Model model) {

		StudySession studySession = studySessionService.findStudySessionInfo(studySessionId);
		StudySessionPasswordForm studySessionPasswordForm = new StudySessionPasswordForm();
		studySessionPasswordForm.setPassword(studySession.getPassword());

		model.addAttribute("studySessionPasswordForm", studySessionPasswordForm);
		return "/teacher/change-session-passowrd";
	}

	@RequestMapping(value = "/course/{courseId}/study-session/{studySessionId}/change-password", method = RequestMethod.POST)
	public String changeStudySessionPassword(@PathVariable("courseId") Integer Id,
			@PathVariable("studySessionId") Integer studySessionId, Model model,
			@ModelAttribute("studySessionPasswordForm") @Valid StudySessionPasswordForm studySessionPasswordForm,
			BindingResult result, RedirectAttributes redirectAttributes) throws ServletException {

		if (result.hasErrors()) {
			model.addAttribute("editStudySessionForm", studySessionPasswordForm);
			// log.debug(editStudySessionForm.toString());
			return "/teacher/change-session-passowrd";
		}
		boolean updatePasswordResult = studySessionService.updateStudySessionPassword(studySessionId,
				studySessionPasswordForm);

		if (updatePasswordResult)
			AppUtil.flash(redirectAttributes, "success", "studySession.success");
		else
			AppUtil.flash(redirectAttributes, "danger", "studySession.failure");

		return "redirect:/teacher/course/{courseId}";

	}

	@RequestMapping(value = "/course/{courseId}/study-session/{studySessionId}/delete", method = RequestMethod.GET)
	public String deleteStudySession(@PathVariable("courseId") Integer courseId,
			@PathVariable("studySessionId") Integer studySessionId, final RedirectAttributes redirectAttributes) {

		boolean deleteResult = studySessionService.deleteStudySession(studySessionId);

		if (deleteResult)
			AppUtil.flash(redirectAttributes, "success", "studySession.deleteSuccess");
		else
			AppUtil.flash(redirectAttributes, "danger", "studySession.deleteFailure");

		return "redirect:/teacher/course/{courseId}";
	}

	@RequestMapping(value = "/course/{courseId}/study-session/{studySessionId}", method = RequestMethod.GET)
	public String sessionPage(@PathVariable("courseId") Integer courseId,
			@PathVariable("studySessionId") Integer studySessionId, Model model) {

		StudySession studySession = studySessionService.findStudySessionInfo(studySessionId);

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDate = formatter.format(studySession.getSessionDate());

		List<Student> students = studySessionService.findStudentByStudySession(studySessionId);
//		for (Student student : students) {
//			log.debug(student.toString());
//		}

		model.addAttribute("studySession", studySession);
		model.addAttribute("sessionDate", sessionDate);
		model.addAttribute("students", students);

		return "/teacher/session-page";
	}

}
