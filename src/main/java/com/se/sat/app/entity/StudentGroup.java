package com.se.sat.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_group")
public class StudentGroup {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "CODE_NAME")
	private String codeName;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;

	private SuperStudentGroup superStudentGroup;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public SuperStudentGroup getSuperStudentGroup() {
		return superStudentGroup;
	}

	public void setSuperStudentGroup(SuperStudentGroup superStudentGroup) {
		this.superStudentGroup = superStudentGroup;
	}

}
