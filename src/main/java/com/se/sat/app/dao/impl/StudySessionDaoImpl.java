package com.se.sat.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
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
	public void insertStudySession(StudySession studySession) {
		persist(studySession);
	}

	@Override
	public void updateStudySession(StudySession studySession) {
		update(studySession);
	}

	@Override
	public void deleteStudySession(StudySession studySession) {
		delete(studySession);
	}

	@Override
	public StudySession findById(int id) {
		StudySession studySession = getByKey(id);
		return studySession;
	}

	@Override
	public List<StudySession> findStudySessionsByCourse(Course course) {
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

}
