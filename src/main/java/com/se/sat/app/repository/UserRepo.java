package com.se.sat.app.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.sat.app.entity.User;

@Repository
public class UserRepo {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public UserRepo(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public void insert(User user){
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
	}
	
	public List<User> findAll(){
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		List<User> users = (List<User>) criteria.list();
		session.getTransaction().commit();
		return users;
	}
	
	public User findUser(String username){
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.like("username", username));
		session.getTransaction().commit();
		return user;
	}
	
}
