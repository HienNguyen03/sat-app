package com.se.sat.app.dao;

import java.util.List;

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.StudySession;

public interface StudySessionDao {

	void insertStudySession(StudySession studySession);

	void updateStudySession(StudySession studySession);

	void deleteStudySession(StudySession studySession);

	StudySession findById(int id);

	List<StudySession> findCourseByCourse(Course course);
}
