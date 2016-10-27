package com.se.sat.app.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {

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
	private Date startStudyDate;
	private Date endStudyDate;
	private String status;
	private StudentGroup studentGroup;
	
}
