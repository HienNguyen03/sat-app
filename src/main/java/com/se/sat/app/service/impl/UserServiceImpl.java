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
import com.se.sat.app.entity.User;
import com.se.sat.app.repository.UserRepo;
import com.se.sat.app.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private UserRepo userRepo;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder){
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

//	@Override
//	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
//	public void signup(SignupForm signupForm) {
//		User user = new User();
//		user.setEmail(signupForm.getEmail());
//		user.setName(signupForm.getName());
//		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
//		user.setVerificationCode(RandomStringUtils.randomAlphabetic(User.RANDOM_CODE_LENGTH));
//		
//		user.getRole().add(Role.UNVERIFIED);
//		userRepo.save(user);
//		// int a = 2/0;
//		
//		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//			
//			@Override
//			public void afterCommit() {
//				try {
//					String verifyLink = AppUtil.hostURL() + "/users/" + user.getVerificationCode() + "/verify";
//					// int a = 2/0;
//					mailSender.send(user.getEmail(), AppUtil.getMessage("verifySubject"), AppUtil.getMessage("verifyEmail", verifyLink));
//					
//					logger.info("Verification mail to " + user.getEmail() + " queued.");
//				} catch (MessagingException e) {
//					logger.info(ExceptionUtils.getStackTrace(e));
//				}
//			}
//		});
//		
//	}

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
