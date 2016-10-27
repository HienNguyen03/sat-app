package com.se.sat.app.entity;

import java.util.Date;

public class Employee {
	
	private String id;
	private String firstname;
	private String lastname;
	private String address;
	private String email;
	private String phone;
	private String password;
	private String profileImageUrl;
	private Date lastAccess;
	private int attempts;
	private Date registeredDate;
	private Date acceptedDate;
	private Date activatedDate;
	private String title;	
	private String status;
	private Employee manager;
}
