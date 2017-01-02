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

import com.se.sat.app.dao.AdminDao;
import com.se.sat.app.dao.StudentDao;
import com.se.sat.app.dao.TeacherDao;
import com.se.sat.app.dao.UserDao;
import com.se.sat.app.dto.CustomUserDetails;
import com.se.sat.app.dto.EditProfileAdminForm;
import com.se.sat.app.dto.EditProfileStudentForm;
import com.se.sat.app.dto.EditProfileTeacherForm;
import com.se.sat.app.dto.SignupAdminForm;
import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.dto.PersonForm;
import com.se.sat.app.dto.SignupStudentForm;
import com.se.sat.app.dto.SignupTeacherForm;
import com.se.sat.app.entity.Admin;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;
import com.se.sat.app.service.UserService;
import com.se.sat.app.util.AppUtil;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserDao userDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;
	private AdminDao adminDao;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao, StudentDao studentDao, TeacherDao teacherDao, AdminDao adminDao,
			PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.studentDao = studentDao;
		this.teacherDao = teacherDao;
		this.adminDao = adminDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public boolean signup(SignupForm signupForm, PersonForm signupPersonForm) {
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
				studentDao.saveStudent(student);

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
				teacherDao.saveTeacher(teacher);

			} else if (signupPersonForm.getPersonType().equals(Role.ADMIN)) {
				Admin admin = new Admin();
				SignupAdminForm signupAdminForm = (SignupAdminForm) signupPersonForm;

				admin.setFirstname(signupAdminForm.getFirstname());
				admin.setLastname(signupAdminForm.getLastname());
				admin.setEmail(signupAdminForm.getEmail());
				admin.setStatus("working");
				admin.setUser(user);

				user.getRole().add(Role.ADMIN);
				adminDao.saveAdmin(admin);

			} else {
				return false;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Transaction rollbacked !");
			return false;
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException(username);

		return new CustomUserDetails(user);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public boolean updateStudentProfile(EditProfileStudentForm editProfileStudentForm) {
		try {
			User loggedInUser = AppUtil.getUserFromSession();
			loggedInUser.getStudent().setFirstname(editProfileStudentForm.getFirstname());
			loggedInUser.getStudent().setLastname(editProfileStudentForm.getLastname());
			loggedInUser.getStudent().setEmail(editProfileStudentForm.getEmail());
			
			studentDao.updateStudent(loggedInUser.getStudent());
			return true;
		} catch(Exception e){
			e.printStackTrace();
			log.info("Transaction rollbacked !");
			return false;
		}
	}

	@Override
	public boolean updateTeacherProfile(EditProfileTeacherForm editProfileTeacherForm) {
		try {
			User loggedInUser = AppUtil.getUserFromSession();
			loggedInUser.getTeacher().setFirstname(editProfileTeacherForm.getFirstname());
			loggedInUser.getTeacher().setLastname(editProfileTeacherForm.getLastname());
			loggedInUser.getTeacher().setEmail(editProfileTeacherForm.getEmail());
			
			teacherDao.updateTeacher(loggedInUser.getTeacher());
			return true;
		} catch(Exception e){
			e.printStackTrace();
			log.info("Transaction rollbacked !");
			return false;
		}
	}

	@Override
	public boolean updateAdminProfile(EditProfileAdminForm editProfileAdminForm) {
		try {
			User loggedInUser = AppUtil.getUserFromSession();
			loggedInUser.getAdmin().setFirstname(editProfileAdminForm.getFirstname());
			loggedInUser.getAdmin().setLastname(editProfileAdminForm.getLastname());
			loggedInUser.getAdmin().setEmail(editProfileAdminForm.getEmail());
			
			adminDao.updateAdmin(loggedInUser.getAdmin());
			return true;
		} catch(Exception e){
			e.printStackTrace();
			log.info("Transaction rollbacked !");
			return false;
		}
	}

	@Override
	public boolean comparePassword(String inputPassword) {
		User user = userDao.findByUsername(AppUtil.getUserFromSession().getUsername());
		if(passwordEncoder.matches(inputPassword, user.getPassword()))
			return true;
		else
			return false;
		
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
