package com.se.sat.app.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.se.sat.app.dto.CourseEnrollmentObjects;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;
import com.se.sat.app.entity.User;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.service.StudySessionService;
import com.se.sat.app.service.UserService;
import com.se.sat.app.util.AppUtil;

@Controller
@RequestMapping("/student")
public class StudentController {

	private static final Logger log = LoggerFactory.getLogger(StudentController.class);

	private CourseService courseService;
	private StudySessionService studySessionService;
	private UserService userService;

	@Autowired
	public StudentController(CourseService courseService, StudySessionService studySessionService,
			UserService userService) {
		this.courseService = courseService;
		this.studySessionService = studySessionService;
		this.userService = userService;
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
		model.addAttribute("teacher", course.getTeacher());
		return "student/course";
	}

	@RequestMapping(value = "/course/{id}/mark-participation", method = RequestMethod.GET)
	public String viewCourseReload(Model model, @PathVariable Integer id) {
		displayParticipationInfo(model, id);
		return "common/others :: studentSession";
	}

	@RequestMapping(value = "/course/{id}/mark-participation", method = RequestMethod.POST)
	public String markParticipation(Model model, @RequestParam(value = "pwd", required = true) String password,
			@PathVariable Integer id, HttpServletRequest request) {

		// password must have at least 4 chars
		if (password != null && password.trim().length() > 4) {
			Student student = AppUtil.getUserFromSession().getStudent();
			Course course = courseService.findCourseInfo(id);
			boolean validToProceed = studySessionService.checkValidForMarkParticipation(course, student);

			if (validToProceed) {
				boolean success = studySessionService.markParticipation(password, course);

				if (success)
					AppUtil.flashModelAttribute(model, "success", "markParticipation.success");
				else
					AppUtil.flashModelAttribute(model, "danger", "markParticipation.failure");

			} else {
				AppUtil.flashModelAttribute(model, "danger", "markParticipation.failure.valid");
			}
		} else {
			AppUtil.flashModelAttribute(model, "danger", "markParticipation.failure.password");
		}

		displayParticipationInfo(model, id);
		return "common/others :: studentSession";

	}

	/**
	 * This method determine to show 'no session found' message participation
	 * list and rate allow mark participation if it is valid
	 */
	protected void displayParticipationInfo(Model model, Integer courseId) {
		Course course = courseService.findCourseInfo(courseId);
		Student student = AppUtil.getUserFromSession().getStudent();

		model.addAttribute("course", course);

		List<StudySession> courseSessions = course.getStudySessions();

		if (!courseSessions.isEmpty()) {
			// show 'no session found' message
			model.addAttribute("noSessionFound", false);

			List<StudySession> studentSessions = studySessionService.findStudySessionsByStudent(student);

			LinkedHashMap<StudySession, Boolean> participationList = studySessionService
					.matchStudentSessions(studentSessions, courseSessions);
			// set participation list
			model.addAttribute("participationList", participationList);

			String participationRate = studySessionService.getParticipationRate(studentSessions, courseSessions);
			// set participation rate
			model.addAttribute("participationRate", participationRate);

			boolean allowMark = studySessionService.checkValidForMarkParticipation(course, student);
			// allow mark participation if it is valid
			model.addAttribute("allowMark", allowMark);

		} else {
			model.addAttribute("noSessionFound", true);
		}
	}

	/**
	 * ENROLLMENT
	 */

	@RequestMapping(value = "/enrollment", method = RequestMethod.GET)
	public String listCourses(Model model) {
		List<CourseEnrollmentObjects> list = courseService.getGroupOfCoursesByTeacher();
		model.addAttribute("enrollmentList", list);

		return "student/courseEnrollmentList";
	}

	@RequestMapping(value = "/enrollment/{courseId}", method = RequestMethod.GET)
	public String viewCourseInfo(Model model, @PathVariable("courseId") Integer courseId) {

		Course course = courseService.findCourseInfo(courseId);
		model.addAttribute("course", course);
		model.addAttribute("teacher", course.getTeacher());

		return "common/others :: courseInfo";

	}

	@RequestMapping(value = "/enrollment", method = RequestMethod.POST)
	public String enrollToCourse(Model model, @RequestParam(value = "courseId", required = true) Integer courseId) {
		Student student = userService.findByUsername(AppUtil.getUserFromSession().getUsername()).getStudent();
		Course course = courseService.findCourseInfo(courseId);

		boolean result = courseService.enrollToACourse(student, course);

		if (result) {
			AppUtil.flashModelAttribute(model, "success", "enrollment.success");
			model.addAttribute("enrolled", true);
		} else {
			AppUtil.flashModelAttribute(model, "danger", "enrollment.failure");
		}

		model.addAttribute("course", course);
		model.addAttribute("teacher", course.getTeacher());

		return "common/others :: courseInfo";

	}
}
