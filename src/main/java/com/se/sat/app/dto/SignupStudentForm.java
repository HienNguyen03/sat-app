package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;


public class SignupStudentForm implements SignupPersonForm {

	@NotNull
	@Size(min=1, message="{Size.signupStudentForm.firstname}")
	private String firstname;
	
	@NotNull
	@Size(min=1, message="{Size.signupStudentForm.lastname}")
	private String lastname;
	
	@NotNull
	@Pattern(regexp=Student.EMAIL_PATTERN, message="{Pattern.signupStudentForm.email}")
	private String email;
	
	@NotNull
	@Size(min=1, message="{Size.signupStudentForm.academicGroup}")
	private String academicGroup;
	
	
	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAcademicGroup() {
		return academicGroup;
	}


	public void setAcademicGroup(String academicGroup) {
		this.academicGroup = academicGroup;
	}


	@Override
	public String toString() {
		return "Signup Teacher Form [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", academicGroup=" + academicGroup + "]";
	}

	@Override
	public Role getPersonType() {
		return Role.STUDENT;
	}
}
