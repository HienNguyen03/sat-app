package com.se.sat.app.validator;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.se.sat.app.dto.CourseForm;
import com.se.sat.app.dto.SignupForm;

@Component
public class CourseFormValidator extends LocalValidatorFactoryBean {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(CourseForm.class);
	}

	@Override
	public void validate(Object target, Errors errors, Object... validationHints) {
		super.validate(target, errors, validationHints);

		if (!errors.hasErrors()) {
			CourseForm courseForm = (CourseForm) target;
			
			Date startDate = courseForm.getStartDate();
			
			
		}
	}
}
