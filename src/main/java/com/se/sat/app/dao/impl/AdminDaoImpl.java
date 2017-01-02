package com.se.sat.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.dao.AbstractDao;
import com.se.sat.app.dao.AdminDao;
import com.se.sat.app.entity.Admin;
import com.se.sat.app.entity.User;

@Repository("AdminDao")
@Transactional
public class AdminDaoImpl extends AbstractDao<Integer, Admin> implements AdminDao {

	private static final Logger log = LoggerFactory.getLogger(AdminDaoImpl.class);
	
	@Override
	public Admin findById(int id) {
		Admin admin = getByKey(id);
		return admin;
	}

	@Override
	public void saveAdmin(Admin admin) {
		persist(admin);
	}
	
	@Override
	public void updateAdmin(Admin admin) {
		update(admin);
	}

	@Override
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Admin admin = (Admin) crit.uniqueResult();
		delete(admin);
	}

	@Override
	public List<Admin> findAllAdmins() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstname"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //To avoid duplicates.
        List<Admin> admins = (List<Admin>) criteria.list();
        return admins;
	}

}
