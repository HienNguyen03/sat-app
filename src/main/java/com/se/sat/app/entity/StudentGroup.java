package com.se.sat.app.entity;

import java.util.Date;

public class StudentGroup {
	
	private int id;
	private String codeName;
	private Date startDate;
	private Date endDate;
	
	// superStudentGroup instead of superStudentGroupId
	private SuperStudentGroup superStudentGroup;
}
