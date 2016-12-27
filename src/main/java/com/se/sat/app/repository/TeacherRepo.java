package com.se.sat.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.entity.Teacher;

@Repository
@Transactional
public class TeacherRepo {
	
	private static final Logger log = LoggerFactory.getLogger(TeacherRepo.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public TeacherRepo(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public void insert(Teacher teacher){
		Session session = sessionFactory.getCurrentSession();
		session.save(teacher);
	}
	
	public List<Teacher> findAll(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Teacher.class);
		List<Teacher> teachers = (List<Teacher>) criteria.list();
		return teachers;
	}
	
	public Teacher findTeacherById(String teacherId){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Teacher.class);
		criteria.add(Restrictions.like("ID", teacherId));
		Teacher result = (Teacher) criteria.uniqueResult();
		return result;
	}
	
}
