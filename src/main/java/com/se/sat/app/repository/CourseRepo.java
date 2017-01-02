package com.se.sat.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Teacher;

@Repository
@Transactional
public class CourseRepo {
	private SessionFactory sessionFactory;

	@Autowired
	public CourseRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void insert(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.save(course);

	}

	public void saveOrUpdate(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(course);
	}

	public void delete(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(course);
	}

	public List<Course> findAll() {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Course.class);
		List<Course> courses = (List<Course>) criteria.list();

		return courses;
	}

	public Course findCourse(Integer id) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Course.class);
		criteria = criteria.add(Restrictions.eq("id", id));
		Course course = (Course) criteria.uniqueResult();

		return course;
	}

	public List<Course> findCourseByTeacher(Teacher teacher) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Course.class);
		criteria = criteria.add(Restrictions.eq("teacher", teacher));
		List<Course> courses = (List<Course>) criteria.list();

		return courses;
	}

}
