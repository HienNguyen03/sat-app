package com.se.sat.app.service.impl;

import java.util.List;

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
import com.se.sat.app.dao.CourseDao;
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
import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;
import com.se.sat.app.service.CourseService;
import com.se.sat.app.service.UserService;
import com.se.sat.app.util.AppUtil;

@Service("courseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseServiceImpl implements CourseService {

	private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

	private UserDao userDao;
	private CourseDao courseDao;
	private StudentDao studentDao;

	@Autowired
	public CourseServiceImpl(UserDao userDao, CourseDao courseDao, StudentDao studentDao) {
		this.userDao = userDao;
		this.courseDao = courseDao;
		this.studentDao = studentDao;
	}

	@Override
	public List<Course> getStudentCourseList() {
		
		return null;
	}

}
