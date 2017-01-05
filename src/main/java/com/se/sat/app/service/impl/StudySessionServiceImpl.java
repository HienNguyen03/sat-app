package com.se.sat.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.se.sat.app.dao.CourseDao;
import com.se.sat.app.dao.StudentDao;
import com.se.sat.app.dao.StudySessionDao;
import com.se.sat.app.dto.EditStudySessionForm;
import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.dto.StudySessionPasswordForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;
import com.se.sat.app.service.StudySessionService;

@Service
public class StudySessionServiceImpl implements StudySessionService {

	private static final Logger log = LoggerFactory.getLogger(StudySessionServiceImpl.class);

	private CourseDao courseDao;
	private StudySessionDao studySessionDao;
	private StudentDao studentDao;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public StudySessionServiceImpl(CourseDao courseDao, StudySessionDao studySessionDao, StudentDao studentDao, PasswordEncoder passwordEncoder) {
		this.courseDao = courseDao;
		this.studySessionDao = studySessionDao;
		this.studentDao = studentDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean insertStudySession(Integer courseId, StudySessionForm studySessionForm) {

		StudySession studySession = new StudySession();

		studySession.setName(studySessionForm.getName());
		studySession.setStartTime(studySessionForm.getStartTime());
		studySession.setEndTime(studySessionForm.getEndTime());
		studySession.setSessionDate(studySessionForm.getSessionDate());
		
		studySession.setPassword(passwordEncoder.encode(studySessionForm.getPassword()));
		studySession.setSessionCategory(studySessionForm.getSessionCategory());

		try {

			Course course = courseDao.findCourseById(courseId);
			studySession.setCourse(course);
			studySessionDao.insertStudySession(studySession);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("Cannot save");
			return false;
		}
	}

	@Override
	public boolean updateStudySessionInfo(Integer id, EditStudySessionForm editStudySessionForm) {

		StudySession studySession = studySessionDao.findById(id);

		studySession.setName(editStudySessionForm.getName());
		studySession.setStartTime(editStudySessionForm.getStartTime());
		studySession.setEndTime(editStudySessionForm.getEndTime());
		studySession.setSessionDate(studySession.getSessionDate());
		studySession.setSessionCategory(editStudySessionForm.getSessionCategory());

		try {
			studySessionDao.updateStudySession(studySession);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("Cannot save");
			return false;
		}

	}

	@Override
	public boolean updateStudySessionPassword(Integer id, StudySessionPasswordForm studySessionPasswordForm) {
		StudySession studySession = studySessionDao.findById(id);

		studySession.setPassword(passwordEncoder.encode(studySessionPasswordForm.getPassword()));

		try {
			studySessionDao.updateStudySession(studySession);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("Cannot save");
			return false;
		}
	}

	@Override
	public boolean deleteStudySession(Integer id) {
		try {
			studySessionDao.deleteStudySessionById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Cannot delete");
			return false;
		}
	}

	@Override
	public List<StudySession> findStudySessionByCourse(Integer courseId) {

		Course course = courseDao.findCourseById(courseId);
		List<StudySession> studySessions = studySessionDao.findStudySessionByCourse(course);

		return studySessions;
	}

	@Override
	public StudySession findStudySessionInfo(Integer id) {

		StudySession studySession = studySessionDao.findById(id);
		return studySession;
	}

	@Override
	public List<Student> findStudentByStudySession(Integer id) {

		StudySession studySession = studySessionDao.findById(id);
		List<Student> students = studentDao.findStudentByStudySession(studySession);
		
		return students;
	}

}
