package com.se.sat.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACADEMIC_GROUP_ID")
	private AcademicGroup academicGroup;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "students")
	private List<CourseGroup> courseGroups = new ArrayList<CourseGroup>();

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "studentPas")
	private List<StudySession> studySessionPas = new ArrayList<StudySession>();

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "studentSts")
	private List<StudySession> studySessionSts = new ArrayList<StudySession>();

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "LAST_ACCESS")
	private Date lastAccess;

	@Column(name = "REGISTERED_DATE")
	private Date registeredDate;

	@Column(name = "STATUS")
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AcademicGroup getAcademicGroup() {
		return academicGroup;
	}

	public void setAcademicGroup(AcademicGroup academicGroup) {
		this.academicGroup = academicGroup;
	}

	public List<CourseGroup> getCourseGroups() {
		return courseGroups;
	}

	public void setCourseGroups(List<CourseGroup> courseGroups) {
		this.courseGroups = courseGroups;
	}

	public List<StudySession> getStudySessionPas() {
		return studySessionPas;
	}

	public void setStudySessionPas(List<StudySession> studySessionPas) {
		this.studySessionPas = studySessionPas;
	}

	public List<StudySession> getStudySessionSts() {
		return studySessionSts;
	}

	public void setStudySessionSts(List<StudySession> studySessionSts) {
		this.studySessionSts = studySessionSts;
	}
	
	

}
