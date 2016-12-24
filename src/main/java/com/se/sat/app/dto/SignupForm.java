package com.se.sat.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.se.sat.app.entity.User;

public class SignupForm {

	@NotNull
	@Size(min=1, max=User.USERNAME_MAX, message="{Size.signupForm.name}")
	private String username;
	
	@NotNull
	@Size(min=4, max=User.PASSWORD_MAX, message="{Size.signupForm.password}")
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Signup Form [username=" + username + ", password=" + password + "]";
	}
}
