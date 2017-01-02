package com.se.sat.app.service;

import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.dto.EditProfileAdminForm;
import com.se.sat.app.dto.EditProfileStudentForm;
import com.se.sat.app.dto.EditProfileTeacherForm;
import com.se.sat.app.dto.PersonForm;
import com.se.sat.app.entity.User;

public interface UserService {
	
	public boolean signup(SignupForm signupForm, PersonForm signupPersonForm);
	public User findByUsername(String username);
	public boolean updateStudentProfile(EditProfileStudentForm editProfileStudentForm);
	public boolean updateTeacherProfile(EditProfileTeacherForm editProfileTeacherForm);
	public boolean updateAdminProfile(EditProfileAdminForm editProfileAdminForm);
	public boolean comparePassword(String inputPassword);

//	public void verify(String verificationCode);
//
//	public void forgotPassword(ForgotPasswordForm forgotPasswordForm);
//
//	public void resetPassword(String forgotPasswordCode, ResetPasswordForm resetPasswordForm, BindingResult result);
//
//	public User findUserInfo(Integer userId);
//
//	public void update(Integer userId, UserEditForm userEditForm);
	
}
