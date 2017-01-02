package com.se.sat.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.dao.AbstractDao;
import com.se.sat.app.dao.UserDao;
import com.se.sat.app.entity.User;

@Repository("userDao")
@Transactional
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Override
	public User findById(int id) {
		User user = getByKey(id);
		
		if (user != null) {
			if (user.isStudent())
				Hibernate.initialize(user.getStudent());
			else if (user.isTeacher())
				Hibernate.initialize(user.getTeacher());
			else if (user.isAdmin())
				Hibernate.initialize(user.getAdmin());
		}
		
		return user;
	}

	@Override
	public User findByUsername(String username) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("username", username));
		User user = (User) criteria.uniqueResult();
		
		if (user != null) {
			if (user.isStudent())
				Hibernate.initialize(user.getStudent());
			else if (user.isTeacher())
				Hibernate.initialize(user.getTeacher());
			else if (user.isAdmin())
				Hibernate.initialize(user.getAdmin());
		}
		
		return user;
	}

	@Override
	public void saveUser(User user) {
		persist(user);
	}
	
	@Override
	public void updateUser(User user) {
		update(user);
	}

	@Override
	public void deleteByUsername(String username) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.like("username", "%" + username + "%"));
		User user = (User) crit.uniqueResult();
		delete(user);
	}

	@Override
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("username"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //To avoid duplicates.
        List<User> users = (List<User>) criteria.list();
        return users;
	}

}
