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

import com.se.sat.app.entity.Admin;

@Repository
@Transactional
public class AdminRepo {
	
	private static final Logger log = LoggerFactory.getLogger(AdminRepo.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public AdminRepo(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public void insert(Admin admin){
		Session session = sessionFactory.getCurrentSession();
		session.save(admin);
	}
	
	public List<Admin> findAll(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Admin.class);
		List<Admin> admins = (List<Admin>) criteria.list();
		return admins;
	}
	
	public Admin findAdminById(String adminId){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Admin.class);
		criteria.add(Restrictions.like("ID", adminId));
		Admin result = (Admin) criteria.uniqueResult();
		return result;
	}
	
}
