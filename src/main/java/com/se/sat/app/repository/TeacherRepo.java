package com.se.sat.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.entity.Teacher;

@Repository
@Transactional
public class TeacherRepo {
	private SessionFactory sessionFactory;

	@Autowired
	public TeacherRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void insert(Teacher teacher) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(teacher);
		
	}

	public List<Teacher> findAll() {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Teacher.class);
		List<Teacher> teachers = (List<Teacher>) criteria.list();
		
		return teachers;
	}

	public Teacher findTeacher(Integer id) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Teacher.class);
		criteria = criteria.add(Restrictions.eq("id", id));
		Teacher teacher = (Teacher) criteria.uniqueResult();
	
		return teacher;
	}
}