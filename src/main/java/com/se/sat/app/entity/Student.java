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

	public static final String EMAIL_PATTERN = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "students")
	private List<Course> courses = new ArrayList<Course>();

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "studentPas")
	private List<StudySession> studySessionPas = new ArrayList<StudySession>();

	@Column(name = "FIRSTNAME", nullable = false)
	private String firstname;

	@Column(name = "LASTNAME", nullable = false)
	private String lastname;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "STATUS", nullable = false)
	private String status;

	@Column(name = "ACADEMIC_GROUP", nullable = false)
	private String academicGroup;

	public Student() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<StudySession> getStudySessionPas() {
		return studySessionPas;
	}

	public void setStudySessionPas(List<StudySession> studySessionPas) {
		this.studySessionPas = studySessionPas;
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", status=" + status + ", academicGroup=" + academicGroup + "]";
	}

}
