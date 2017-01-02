package com.se.sat.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.entity.Student;

@Repository
@Transactional
public class StudentRepo {
	
	private static final Logger log = LoggerFactory.getLogger(StudentRepo.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public StudentRepo(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public void insert(Student student){
		Session session = sessionFactory.getCurrentSession();
		session.save(student);
	}
	
	public List<Student> findAll(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);
		List<Student> students = (List<Student>) criteria.list();
		return students;
	}
	
	public Student findStudentById(String studentId){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);
		criteria.add(Restrictions.like("ID", studentId));
		Student result = (Student) criteria.uniqueResult();
		return result;
	}
	
}
