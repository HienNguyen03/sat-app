package com.se.sat.app.service;

import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.dto.SignupPersonForm;

public interface UserService {
	
	public boolean signup(SignupForm signupForm, SignupPersonForm signupPersonForm);

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
