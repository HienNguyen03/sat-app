package com.se.sat.app.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransactionException;
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

import com.se.sat.app.dto.CustomUserDetails;
import com.se.sat.app.dto.SignupAdminForm;
import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.dto.SignupPersonForm;
import com.se.sat.app.dto.SignupStudentForm;
import com.se.sat.app.dto.SignupTeacherForm;
import com.se.sat.app.entity.Admin;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;
import com.se.sat.app.repository.AdminRepo;
import com.se.sat.app.repository.StudentRepo;
import com.se.sat.app.repository.TeacherRepo;
import com.se.sat.app.repository.UserRepo;
import com.se.sat.app.service.UserService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepo userRepo;
	private StudentRepo studentRepo;
	private TeacherRepo teacherRepo;
	private AdminRepo adminRepo;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepo userRepo, StudentRepo studentRepo, TeacherRepo teacherRepo, AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.studentRepo = studentRepo;
		this.teacherRepo = teacherRepo;
		this.adminRepo = adminRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean signup(SignupForm signupForm, SignupPersonForm signupPersonForm) {
		User user = new User();
		user.setUsername(signupForm.getUsername());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));

		try {
			if (signupPersonForm.getPersonType().equals(Role.STUDENT)) {
				Student student = new Student();
				SignupStudentForm signupStudentForm = (SignupStudentForm) signupPersonForm;
	
				student.setFirstname(signupStudentForm.getFirstname());
				student.setLastname(signupStudentForm.getLastname());
				student.setEmail(signupStudentForm.getEmail());
				student.setAcademicGroup(signupStudentForm.getAcademicGroup());
				student.setStatus("studying");
				student.setUser(user);
	
				user.getRole().add(Role.STUDENT);
				studentRepo.insert(student);
				
			} else if (signupPersonForm.getPersonType().equals(Role.TEACHER)) {
				Teacher teacher = new Teacher();
				SignupTeacherForm signupTeacherForm = (SignupTeacherForm) signupPersonForm;
	
				teacher.setFirstname(signupTeacherForm.getFirstname());
				teacher.setLastname(signupTeacherForm.getLastname());
				teacher.setEmail(signupTeacherForm.getEmail());
				teacher.setDepartment(signupTeacherForm.getDepartment());
				teacher.setStatus("working");
				teacher.setUser(user);
	
				user.getRole().add(Role.TEACHER);
				teacherRepo.insert(teacher);
				
			} else {
				Admin admin = new Admin();
				SignupAdminForm signupAdminForm = (SignupAdminForm) signupPersonForm;
	
				admin.setFirstname(signupAdminForm.getFirstname());
				admin.setLastname(signupAdminForm.getLastname());
				admin.setEmail(signupAdminForm.getEmail());
				admin.setStatus("working");
				admin.setUser(user);
	
				user.getRole().add(Role.ADMIN);
				adminRepo.insert(admin);
				
			}
			
			return true;
		} catch (Exception e){
			e.printStackTrace();
			log.info("Transaction rollbacked !");
			return false;
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findUserByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException(username);

		return new CustomUserDetails(user);
	}

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	// public void verify(String verificationCode) {
	//
	// Integer userId = AppUtil.getUserFromSession().getId();
	// User user = userRepo.findOne(userId);
	//
	// AppUtil.validate(verificationCode.equals(user.getVerificationCode()),
	// "incorrect", "verification code");
	// AppUtil.validate(user.getRole().contains(Role.UNVERIFIED),
	// "alreadyVerified");
	//
	// user.setVerificationCode(null);
	// user.getRole().remove(Role.UNVERIFIED);
	// userRepo.save(user);
	//
	// }

	// @Override
	// public User findUserInfo(Integer userId) {
	// User user = userRepo.findOne(userId);
	// User loggedInUser = AppUtil.getUserFromSession();
	//
	// if(loggedInUser == null || (loggedInUser.getId() != user.getId() &&
	// !loggedInUser.isAdmin())){
	// user.setEmail("Confidential");
	// }
	//
	// return user;
	// }

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	// public void update(Integer userId, UserEditForm userEditForm) {
	//
	// User logginUser = AppUtil.getUserFromSession();
	// AppUtil.validate(logginUser.isAdmin() || logginUser.getId() == userId,
	// "noPermission");
	//
	// User user = userRepo.findOne(userId);
	// user.setName(userEditForm.getName());
	// if(logginUser.isAdmin())
	// user.setRole(userEditForm.getRole());
	//
	// userRepo.save(user);
	//
	// }

}
