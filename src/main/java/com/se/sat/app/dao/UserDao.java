package com.se.sat.app.dao;

import java.util.List;

import com.se.sat.app.entity.User;

public interface UserDao {
	
	User findById(int id);
	User findByUsername(String username);
	void saveUser(User user);
	void updateUser(User user);
	void deleteByUsername(String username);
	List<User> findAllUsers();
	
}
