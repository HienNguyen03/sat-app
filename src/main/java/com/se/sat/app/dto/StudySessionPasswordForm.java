package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudySessionPasswordForm {

	@NotNull
	@Size(min = 4, message = "{Size.studySessionPasswordForm.password}")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "StudySessionPasswordForm [password=" + password + "]";
	}

}
