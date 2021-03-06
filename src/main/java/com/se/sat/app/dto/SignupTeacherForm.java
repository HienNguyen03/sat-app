package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.User.Role;


public class SignupTeacherForm implements PersonForm {

	@NotNull
	@Size(min=1, message="{Size.signupTeacherForm.firstname}")
	private String firstname;
	
	@NotNull
	@Size(min=1, message="{Size.signupTeacherForm.lastname}")
	private String lastname;
	
	@NotNull
	@Pattern(regexp=Student.EMAIL_PATTERN, message="{Pattern.signupTeacherForm.email}")
	private String email;
	
	@NotNull
	@Size(min=1, message="{Size.signupTeacherForm.department}")
	private String department;
	
	
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


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	@Override
	public String toString() {
		return "Signup Teacher Form [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", department=" + department + "]";
	}

	@Override
	public Role getPersonType() {
		return Role.TEACHER;
	}
}
