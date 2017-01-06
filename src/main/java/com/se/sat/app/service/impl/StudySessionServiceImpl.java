package com.se.sat.app.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.se.sat.app.util.AppUtil;

@Service("studySessionService")
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
			if (studentSessions.contains(studySession))
				list.put(studySession, true);
			else
				list.put(studySession, false);

		}
		return list;
	}

	@Override

	public String getParticipationRate(List<StudySession> studentSessions, List<StudySession> courseSessions) {
		DecimalFormat df = new DecimalFormat("###");
		return df.format(((double) studentSessions.size() / (double) courseSessions.size()) * 100) + "%";
	}


	@Override
	public boolean checkValidForMarkParticipation(Course course, Student student) {
		StudySession theLastStudySession = studySessionDao.findTheLastStudySessionByCourse(course);

		if (theLastStudySession != null) {
			// check the current date with the date the study session happens
			Date currentDateTime = new Date();

			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

			Date currentDate, givenDate;
			Date currentTime, givenStartTime, givenEndTime;

			try {
				currentDate = sdfDate.parse(sdfDate.format(currentDateTime));
				givenDate = sdfDate.parse(sdfDate.format(theLastStudySession.getSessionDate()));

				// if the date is fine
				if (currentDate.compareTo(givenDate) == 0) {
					
					// continue to check time
					currentTime = sdfTime.parse(sdfTime.format(currentDateTime));
					givenStartTime = sdfTime.parse(sdfTime.format(theLastStudySession.getStartTime()));
					givenEndTime = sdfTime.parse(sdfTime.format(theLastStudySession.getEndTime()));

					// if time is valid, allow to mark participation
					if (currentTime.compareTo(givenStartTime) >= 0 && currentTime.compareTo(givenEndTime) < 0) {

						StudySession theLastStudySessionParticipation = studySessionDao
								.findTheLastStudySessionByStudentAndCourse(student, course);

						// if student attended to any session of this course
						if (theLastStudySessionParticipation != null) {
							// if those sessions are not matched, means 
							// student did not checked the newest study session
							if (theLastStudySessionParticipation.getId() != theLastStudySession.getId()) {
								// then do it
								return true;
							}
						} else {
							return true;
						}
					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
		}

		return false;

	}

	public List<Student> findStudentByStudySession(Integer id) {

		StudySession studySession = studySessionDao.findById(id);
		List<Student> students = studentDao.findStudentByStudySession(studySession);

		return students;
	}

	@Override
	public boolean markParticipation(String inputPassword, Course course) {
		// compare password
		StudySession theLastStudySession = studySessionDao.findTheLastStudySessionByCourse(course);
		boolean success = passwordEncoder.matches(inputPassword, theLastStudySession.getPassword());

		// mark
		if (success) {
			Student student = AppUtil.getUserFromSession().getStudent();
			student = studentDao.findById(student.getId());

			student.getStudySessionPas().add(theLastStudySession);
			theLastStudySession.getStudentPas().add(student);

			studySessionDao.updateStudySession(theLastStudySession);
			studentDao.updateStudent(student);
			return true;
		} else {
			return false;
		}

	}

}
