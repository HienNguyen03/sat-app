package com.se.sat.app.service;

import java.util.List;

import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.StudySession;

public interface StudySessionService {

	public boolean addStudySession(Integer courseId, StudySessionForm studySessionForm);

	public boolean updateStudySession(Integer id, StudySessionForm studySessionForm);
	
	public boolean deleteStudySession(Integer id);
	
	public List<StudySession> findSessionByCourse(Course course);
	
	public  StudySession findStudySessionInfo(Integer id);
}
