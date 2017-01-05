package com.se.sat.app.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public StudySessionServiceImpl(CourseDao courseDao, StudySessionDao studySessionDao, StudentDao studentDao,
			PasswordEncoder passwordEncoder) {
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
	public List<StudySession> findStudySessionsByStudent(Student student) {
		List<StudySession> studySessions = new ArrayList<StudySession>();
		studySessions = studySessionDao.findStudySessionsByStudent(student);

		return studySessions;
	}

	@Override
	public LinkedHashMap<StudySession, Boolean> matchStudentSessions(List<StudySession> studentSessions,
			List<StudySession> courseSessions) {

		LinkedHashMap<StudySession, Boolean> list = new LinkedHashMap<StudySession, Boolean>();

		for (StudySession studySession : courseSessions) {
			if (studentSessions.contains(studySession)) {
				list.put(studySession, true);
				log.info(">" + studySession.getId());
			} else {
				list.put(studySession, false);
				log.info(">" + studySession.getId());
			}
		}
		return list;
	}

	@Override
	public boolean checkValidTimeForMarkParticipation(Course course) {
		List<StudySession> studySessions = studySessionDao.findStudySessionByCourse(course);

		if (!studySessions.isEmpty()) {
			final Iterator<StudySession> itr = studySessions.iterator();
			StudySession lastStudySession = itr.next();
			while (itr.hasNext()) {
				lastStudySession = itr.next();
			}

			log.info("result: " + lastStudySession.toString());

			// check current date
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
			Date currentDateTime = new Date();

			Date currentDate, givenDate;
			Date currentTime, givenStartTime, givenEndTime;

			try {
				currentDate = sdfDate.parse(sdfDate.format(currentDateTime));
				givenDate = sdfDate.parse(sdfDate.format(lastStudySession.getSessionDate()));

				if (currentDate.compareTo(givenDate) == 0) {
					currentTime = sdfTime.parse(sdfTime.format(currentDateTime));
					givenStartTime = sdfTime.parse(sdfTime.format(lastStudySession.getStartTime()));
					givenEndTime = sdfTime.parse(sdfTime.format(lastStudySession.getEndTime()));

					if (currentTime.compareTo(givenStartTime) >= 0 && currentTime.compareTo(givenEndTime) <= 0) {
						return true;
					}
				} else {
					return false;
				}

			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}

			return false;
		}

		return false;

	}

	public List<Student> findStudentByStudySession(Integer id) {

		StudySession studySession = studySessionDao.findById(id);
		List<Student> students = studentDao.findStudentByStudySession(studySession);

		return students;
	}

}
