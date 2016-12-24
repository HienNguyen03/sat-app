package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;

public class SignupTeacherForm {

	@NotNull
	private String firstname;
	
	@NotNull
	private String lastname;
	
	@NotNull
	private String email;
	
	@NotNull
	private String department;
	
	@NotNull
	private String status;
	
	
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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Signup Teacher Form [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", department=" + department + ", status=" + status + "]";
	}
}
