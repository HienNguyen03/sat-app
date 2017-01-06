package com.se.sat.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.dao.AbstractDao;
import com.se.sat.app.dao.StudySessionDao;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;

@Repository("StudySessionDao")
@Transactional
public class StudySessionDaoImpl extends AbstractDao<Integer, StudySession> implements StudySessionDao {

	@Override
	public StudySession findById(int id) {
		StudySession studySession = getByKey(id);
		if (studySession != null)
			Hibernate.initialize(studySession.getStudentPas());
		return studySession;
	}
	
	@Override
	public void insertStudySession(StudySession studySession) {
		persist(studySession);
	}

	@Override
	public void updateStudySession(StudySession studySession) {
		update(studySession);
	}

	@Override
	public void deleteStudySessionById(Integer id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		StudySession studySession = (StudySession) criteria.uniqueResult();

		if (studySession != null) {

			delete(studySession);
		}
	}
	
	@Override
	public List<StudySession> findStudySessionByCourse(Course course) {
		Criteria criteria = createEntityCriteria();
		criteria = criteria.add(Restrictions.eq("course", course));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<StudySession> studySessions = (List<StudySession>) criteria.list();	

		return studySessions;
	}

	@Override
	public List<StudySession> findStudySessionsByStudent(Student student) {
		String hql = "select SS FROM StudySession SS join SS.studentPas SP "
				+ "WHERE SP.id = :studentId ORDER BY SS.id ASC";
		Query query = getSession().createQuery(hql);
		query.setInteger("studentId", student.getId());

		List<StudySession> studySessions = (List<StudySession>) query.list();

		return studySessions;
	}

	@Override
	public StudySession findTheLastStudySessionByCourse(Course course) {
		String hql = "SELECT SS FROM StudySession SS JOIN SS.course SC "
				+ "WHERE SC.id = :courseId ORDER BY SS.id DESC";
		Query query = getSession().createQuery(hql);
		query.setInteger("courseId", course.getId());
		query.setMaxResults(1);

		StudySession studySession = (StudySession) query.uniqueResult();
		if (studySession != null)
			Hibernate.initialize(studySession.getStudentPas());
		return studySession;
	}
	
	@Override
	public StudySession findTheLastStudySessionByStudentAndCourse(Student student, Course course) {
		String hql = "SELECT SS FROM StudySession SS JOIN SS.studentPas SP JOIN SS.course SC "
				+ "WHERE SP.id = :studentId AND SC.id = :courseId ORDER BY SS.id DESC";
		Query query = getSession().createQuery(hql);
		query.setInteger("studentId", student.getId());
		query.setInteger("courseId", course.getId());
		query.setMaxResults(1);

		StudySession studySession = (StudySession) query.uniqueResult();
		if (studySession != null)
			Hibernate.initialize(studySession.getStudentPas());
		return studySession;
	}
}
