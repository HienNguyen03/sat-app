package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;

public class EditProfileTeacherForm {
	
	@NotNull
	@Size(min=1, message="{Size.editProfileTeacherForm.firstname}")
	private String firstname;
	
	@NotNull
	@Size(min=1, message="{Size.editProfileTeacherForm.lastname}")
	private String lastname;
	
	@NotNull
	@Pattern(regexp=Student.EMAIL_PATTERN, message="{Pattern.editProfileTeacherForm.email}")
	private String email;
	
	@NotNull
	@Size(min=1, message="{Size.editProfileTeacherForm.department}")
	private String department;
	
	@NotNull
	@Size(min=User.PASSWORD_MIN, max=User.PASSWORD_MAX, message="{Size.editProfileTeacherForm.password}")
	private String password;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Teacher Profile Edit Form [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", department=" + department + "]";
	}
	
}
