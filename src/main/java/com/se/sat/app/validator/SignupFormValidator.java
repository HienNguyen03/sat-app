package com.se.sat.app.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.se.sat.app.dao.UserDao;
import com.se.sat.app.dto.SignupForm;
import com.se.sat.app.entity.User;

@Component
public class SignupFormValidator extends LocalValidatorFactoryBean {
	
	private UserDao userDao;
	
	@Autowired
	public void setUserRepo(UserDao userDao){
		this.userDao = userDao;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(SignupForm.class);
	}
	
	@Override
	public void validate(Object target, Errors errors, Object... validationHints) {
		super.validate(target, errors, validationHints);
		
		if(!errors.hasErrors()){
			SignupForm signupForm = (SignupForm) target;
			User user = userDao.findByUsername(signupForm.getUsername());
			if(user != null){
				errors.rejectValue("username", "usernameIsUsed");
			}
		}
			
	}
	
}
