package com.se.sat.app.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.se.sat.app.dto.EditStudySessionForm;
import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.entity.Course;
import com.se.sat.app.dto.StudySessionPasswordForm;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;

public interface StudySessionService {

	public boolean insertStudySession(Integer courseId, StudySessionForm studySessionForm);
	public boolean updateStudySessionInfo(Integer id, EditStudySessionForm editStudySessionForm);
	public boolean updateStudySessionPassword (Integer id,StudySessionPasswordForm studySessionPasswordForm);
	public boolean deleteStudySession(Integer id);
	
	public List<StudySession> findStudySessionsByStudent(Student student);
	public List<StudySession> findStudySessionByCourse(Integer courseId);
	public StudySession findStudySessionInfo(Integer id);
	
	
	public boolean checkValidForMarkParticipation(Course course, Student student);
	public List<Student> findStudentByStudySession(Integer id);
	public boolean markParticipation(String inputPassword, Course course);
	
	public LinkedHashMap<StudySession, Boolean> matchStudentSessions(List<StudySession> studentSessions, List<StudySession> courseSessions);
	public String getParticipationRate(List<StudySession> studentSessions, List<StudySession> courseSessions);
	public Student findStudentInfoInSession(Integer studentId);
	public StudySession findLatestStudySessionByCourse(Course course);
}
