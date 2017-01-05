package com.se.sat.app.controller;

import java.text.DecimalFormat;
import java.util.Hashtable;
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

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.entity.User;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.service.StudySessionService;
import com.se.sat.app.util.AppUtil;

@Controller
@RequestMapping("/student")
public class StudentController {

	private static final Logger log = LoggerFactory.getLogger(StudentController.class);

	private CourseService courseService;
	private StudySessionService studySessionService;

	@Autowired
	public StudentController(CourseService courseService, StudySessionService studySessionService) {
		this.courseService = courseService;
		this.studySessionService = studySessionService;
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
		Student student = AppUtil.getUserFromSession().getStudent();

		// course and teacher info
		model.addAttribute("course", course);
		model.addAttribute("teacher", course.getTeacher());

		List<StudySession> courseSessions = course.getStudySessions();

		if (!courseSessions.isEmpty()) {
			model.addAttribute("noSessionFound", false);

			List<StudySession> studentSessions = studySessionService.findStudySessionsByStudent(student);

			LinkedHashMap<StudySession, Boolean> participationList = studySessionService
					.matchStudentSessions(studentSessions, courseSessions);

			DecimalFormat df = new DecimalFormat("###");
			String participationRate = df
					.format(((double) studentSessions.size() / (double) courseSessions.size()) * 100) + "%";

			log.info("rate: " + participationRate);

			// participation list and rate
			model.addAttribute("participationList", participationList);
			model.addAttribute("participationRate", participationRate);

			boolean allowMark = studySessionService.checkValidTimeForMarkParticipation(course);

			// allow mark participation if it is valid
			model.addAttribute("allowMark", allowMark);
			
		} else {
			model.addAttribute("noSessionFound", true);
		}

		return "student/course";
	}
	
	@RequestMapping(value = "/course/{id}/reload", method = RequestMethod.GET)
	public String viewCourseReload(Model model, @PathVariable Integer id) {
		log.info("run run run");
		Course course = courseService.findCourseInfo(id);
		Student student = AppUtil.getUserFromSession().getStudent();

		// course and teacher info
		model.addAttribute("course", course);
		model.addAttribute("teacher", course.getTeacher());

		List<StudySession> courseSessions = course.getStudySessions();

		if (!courseSessions.isEmpty()) {
			model.addAttribute("noSessionFound", false);

			List<StudySession> studentSessions = studySessionService.findStudySessionsByStudent(student);

			LinkedHashMap<StudySession, Boolean> participationList = studySessionService
					.matchStudentSessions(studentSessions, courseSessions);

			DecimalFormat df = new DecimalFormat("###");
			String participationRate = df
					.format(((double) studentSessions.size() / (double) courseSessions.size()) * 100) + "%";

			log.info("rate: " + participationRate);

			// participation list and rate
			model.addAttribute("participationList", participationList);
			model.addAttribute("participationRate", participationRate);

			boolean allowMark = studySessionService.checkValidTimeForMarkParticipation(course);

			// allow mark participation if it is valid
			model.addAttribute("allowMark", allowMark);
			
		} else {
			model.addAttribute("noSessionFound", true);
		}

		return "common/other :: studentSession";
	}
	
	@RequestMapping(value = "/course/{id}/mark-participation", method = RequestMethod.POST)
	public String markParticipation(Model model, @PathVariable Integer id, HttpServletRequest request) {
		
		String inputPassword = (String) request.getParameter("sessionPassword");
		
		return null;
	}

}
