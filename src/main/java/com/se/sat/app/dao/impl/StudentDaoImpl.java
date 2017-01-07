package com.se.sat.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.dao.AbstractDao;
import com.se.sat.app.dao.StudentDao;
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.StudySession;
import com.se.sat.app.entity.User;

@Repository("studentDao")
@Transactional
public class StudentDaoImpl extends AbstractDao<Integer, Student> implements StudentDao {

	private static final Logger log = LoggerFactory.getLogger(StudentDaoImpl.class);

	@Override
	public Student findById(int id) {
		Student student = getByKey(id);

		if (student != null) {
			Hibernate.initialize(student.getStudySessionPas());
			Hibernate.initialize(student.getCourses());
		}
		return student;
	}

	@Override
	public void saveStudent(Student student) {
		persist(student);
	}

	@Override
	public void updateStudent(Student student) {
		update(student);
	}

	@Override
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Student student = (Student) crit.uniqueResult();
		if (student != null)
			delete(student);
	}

	@Override
	public List<Student> findAllStudents() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstname"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Student> students = (List<Student>) criteria.list();
		return students;
	}

	@Override
	public List<Student> findStudentByStudySession(StudySession studySession) {

		String hql = "SELECT stu " + "FROM Student stu join stu.studySessionPas ss " + "WHERE ss.id = :studySessionId";

		Query query = getSession().createQuery(hql);
		query.setInteger("studySessionId", studySession.getId());

		List<Student> students = (List<Student>) query.list();
		return students;

	}

}
