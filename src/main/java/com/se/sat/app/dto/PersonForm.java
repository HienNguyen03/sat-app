package com.se.sat.app.dto;

import com.se.sat.app.entity.User.Role;

public interface PersonForm {
	
	public String getFirstname();
	
	public void setFirstname(String firstname);
	
	public String getLastname();

	public void setLastname(String lastname);

	public String getEmail();

	public void setEmail(String email);
	
	public Role getPersonType();
	
}
