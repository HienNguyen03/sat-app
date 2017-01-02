package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;

public class EditProfileAdminForm {
	
	@NotNull
	@Size(min=1, message="{Size.editProfileAdminForm.firstname}")
	private String firstname;
	
	@NotNull
	@Size(min=1, message="{Size.editProfileAdminForm.lastname}")
	private String lastname;
	
	@NotNull
	@Pattern(regexp=Student.EMAIL_PATTERN, message="{Pattern.editProfileAdminForm.email}")
	private String email;
	
	@NotNull
	@Size(min=User.PASSWORD_MIN, max=User.PASSWORD_MAX, message="{Size.editProfileAdminForm.password}")
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin Profile Edit Form [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + "]";
	}
	
}
