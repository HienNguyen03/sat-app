package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.User;
import com.se.sat.app.entity.User.Role;

public class EditProfileStudentForm {
	
	@NotNull
	@Size(min=1, message="{Size.editProfileStudentForm.firstname}")
	private String firstname;
	
	@NotNull
	@Size(min=1, message="{Size.editProfileStudentForm.lastname}")
	private String lastname;
	
	@NotNull
	@Pattern(regexp=Student.EMAIL_PATTERN, message="{Pattern.editProfileStudentForm.email}")
	private String email;
	
	@NotNull
	@Size(min=1, message="{Size.editProfileStudentForm.academicGroup}")
	private String academicGroup;
	
	@NotNull
	@Size(min=User.PASSWORD_MIN, max=User.PASSWORD_MAX, message="{Size.editProfileStudentForm.password}")
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

	public String getAcademicGroup() {
		return academicGroup;
	}

	public void setAcademicGroup(String academicGroup) {
		this.academicGroup = academicGroup;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Student Profile Edit Form [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", academicGroup=" + academicGroup + "]";
	}
	
}
