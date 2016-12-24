package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;

public class SignupStudentForm {

	@NotNull
	private String firstname;
	
	@NotNull
	private String lastname;
	
	@NotNull
	private String email;
	
	@NotNull
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
}
