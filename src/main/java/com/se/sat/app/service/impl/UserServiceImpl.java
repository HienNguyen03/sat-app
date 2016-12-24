package com.se.sat.app.service.impl;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.BindingResult;

import com.se.sat.app.dto.CustomUserDetails;
import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;
import com.se.sat.app.repository.UserRepo;
import com.se.sat.app.service.UserService;
import com.se.sat.app.util.AppUtil;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepo userRepo;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder){
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void signup(SignupForm signupForm) {
		User user = new User();
		user.setUsername(signupForm.getUsername());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		
		log.info(user.getUsername());
		log.info(user.getPassword());
		
		boolean result = user.getRole().add(Role.STUDENT);
		log.info(String.valueOf(result));
		//userRepo.insert(user);
		// int a = 2/0;
				
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findUser(username);
		
		if(user == null)
			throw new UsernameNotFoundException(username);
		
		return new CustomUserDetails(user);
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//	public void verify(String verificationCode) {
//		
//		Integer userId = AppUtil.getUserFromSession().getId();
//		User user = userRepo.findOne(userId);
//		
//		AppUtil.validate(verificationCode.equals(user.getVerificationCode()), "incorrect", "verification code");
//		AppUtil.validate(user.getRole().contains(Role.UNVERIFIED), "alreadyVerified");
//		
//		user.setVerificationCode(null);
//		user.getRole().remove(Role.UNVERIFIED);
//		userRepo.save(user);
//		
//	}

//	@Override
//	public User findUserInfo(Integer userId) {
//		User user = userRepo.findOne(userId);
//		User loggedInUser = AppUtil.getUserFromSession();
//		
//		if(loggedInUser == null || (loggedInUser.getId() != user.getId() && !loggedInUser.isAdmin())){
//			user.setEmail("Confidential");
//		}
//		
//		return user;
//	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//	public void update(Integer userId, UserEditForm userEditForm) {
//		
//		User logginUser = AppUtil.getUserFromSession();
//		AppUtil.validate(logginUser.isAdmin() || logginUser.getId() == userId, "noPermission");
//		
//		User user = userRepo.findOne(userId);
//		user.setName(userEditForm.getName());
//		if(logginUser.isAdmin())
//			user.setRole(userEditForm.getRole());
//		
//		userRepo.save(user);
//		
//	}

}
