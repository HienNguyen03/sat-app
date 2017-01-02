package com.se.sat.app.dao;

import java.util.List;

import com.se.sat.app.entity.Admin;
import com.se.sat.app.entity.User;

public interface AdminDao {
	
	Admin findById(int id);
	void saveAdmin(Admin admin);
	void updateAdmin(Admin admin);
	void deleteById(int id);
	List<Admin> findAllAdmins();
	
}
