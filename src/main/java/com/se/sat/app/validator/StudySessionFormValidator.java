package com.se.sat.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.se.sat.app.dto.StudySessionForm;

@Component
public class StudySessionFormValidator extends LocalValidatorFactoryBean {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(StudySessionForm.class);
	}

	@Override
	public void validate(Object target, Errors errors, Object... validationHints) {
		super.validate(target, errors, validationHints);

		if (!errors.hasErrors()) {
			StudySessionForm studySessionForm = (StudySessionForm) target;

			if (studySessionForm.getStartTime().before(studySessionForm.getEndTime()) != true) {
				errors.rejectValue("endTime", "endTimeGreaterThan");
			}
		}
	}

}
