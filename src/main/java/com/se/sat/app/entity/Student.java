package com.se.sat.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User user;

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

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "ACADEMIC_GROUP")
	private String academicGroup;

	public Student() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcademicGroup() {
		return academicGroup;
	}

	public void setAcademicGroup(String academicGroup) {
		this.academicGroup = academicGroup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
