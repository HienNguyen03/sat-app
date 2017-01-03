package com.se.sat.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se.sat.app.dao.CourseDao;
import com.se.sat.app.dao.StudySessionDao;
import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.StudySession;
import com.se.sat.app.service.StudySessionService;

@Service
public class StudySessionServiceImpl implements StudySessionService{
	
	private static final Logger log = LoggerFactory.getLogger(StudySessionServiceImpl.class);

	private CourseDao courseDao;
	private StudySessionDao studySessionDao;
	
	@Autowired
	public StudySessionServiceImpl(CourseDao courseDao,StudySessionDao studySessionDao) {
		this.courseDao = courseDao;
		this.studySessionDao = studySessionDao;
	}

	@Override
	public boolean insertStudySession(Integer courseId, StudySessionForm studySessionForm) {
		
		StudySession studySession = new StudySession();
		
		studySession.setName(studySessionForm.getName());
		studySession.setStartTime(studySessionForm.getStartTime());
		studySession.setSessionDate(studySessionForm.getSessionDate());
		studySession.setPassword(studySessionForm.getPassword());
		studySession.setSessionCategory(studySessionForm.getSessionCategory());
		
		try{
			
			Course course = courseDao.findCourseById(courseId);
			studySession.setCourse(course);
			studySessionDao.insertStudySession(studySession);
			
			return true;
			
		}catch (Exception e) {
			e.printStackTrace();
			//log.debug("Cannot save");
			return false;
		}
	}

	@Override
	public boolean updateStudySession(Integer id, StudySessionForm studySessionForm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteStudySession(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<StudySession> findSessionByCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudySession findStudySessionInfo(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
