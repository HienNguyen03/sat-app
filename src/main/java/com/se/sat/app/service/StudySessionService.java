package com.se.sat.app.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;

public interface StudySessionService {

	public boolean insertStudySession(Integer courseId, StudySessionForm studySessionForm);
	public boolean updateStudySession(Integer id, StudySessionForm studySessionForm);
	public boolean deleteStudySession(Integer id);
	
	public List<StudySession> findSessionByCourse(Course course);
	public List<StudySession> findStudySessionsByStudent(Student student);
	public StudySession findStudySessionInfo(Integer id);
	
	public LinkedHashMap<StudySession, Boolean> matchStudentSessions(List<StudySession> studentSessions, List<StudySession> courseSessions);
	public boolean checkValidTimeForMarkParticipation(Course course);
}
