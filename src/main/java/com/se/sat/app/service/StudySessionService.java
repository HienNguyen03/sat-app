package com.se.sat.app.service;

import java.util.List;

import com.se.sat.app.dto.EditStudySessionForm;
import com.se.sat.app.dto.StudySessionForm;
import com.se.sat.app.dto.StudySessionPasswordForm;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;

public interface StudySessionService {

	public boolean insertStudySession(Integer courseId, StudySessionForm studySessionForm);
	public boolean updateStudySessionInfo(Integer id, EditStudySessionForm editStudySessionForm);
	public boolean updateStudySessionPassword (Integer id,StudySessionPasswordForm studySessionPasswordForm);
	public boolean deleteStudySession(Integer id);
	
	public List<StudySession> findStudySessionByCourse(Integer courseId);
	public StudySession findStudySessionInfo(Integer id);
	public List<Student> findStudentByStudySession(Integer id);
}
