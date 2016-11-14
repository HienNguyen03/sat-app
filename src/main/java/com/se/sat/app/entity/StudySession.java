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
@Table(name = "study_session")
public class StudySession {

	@Id
	@Column(name = "ID")
	private int id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SESSION_CATEGORY_ID")
	private SessionCategory sessionCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID")
	private Course course;
			
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "studySessions")
	private List<CourseGroup> courseGroups = new ArrayList<CourseGroup>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "paticipation", joinColumns = @JoinColumn(name = "STUDY_SESSION_ID"), inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
	private List<Student> studentPas = new ArrayList<Student>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "student_session", joinColumns = @JoinColumn(name = "STUDY_SESSION_ID"), inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
	private List<Student> studentSts = new ArrayList<Student>();

	@Column(name = "NAME")
	private String name;

	@Column(name = "START_TIME")
	private Date startTime;

	@Column(name = "END_TIME")
	private Date endTime;

	@Column(name = "SESSION_DATE")
	private Date sessionDate;

	@Column(name = "PASSWORD")
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public SessionCategory getSessionCategory() {
		return sessionCategory;
	}

	public void setSessionCategory(SessionCategory sessionCategory) {
		this.sessionCategory = sessionCategory;
	}

	public List<CourseGroup> getCourseGroups() {
		return courseGroups;
	}

	public void setCourseGroups(List<CourseGroup> courseGroups) {
		this.courseGroups = courseGroups;
	}

	public List<Student> getStudentPas() {
		return studentPas;
	}

	public void setStudentPas(List<Student> studentPas) {
		this.studentPas = studentPas;
	}

	public List<Student> getStudentSts() {
		return studentSts;
	}

	public void setStudentSts(List<Student> studentSts) {
		this.studentSts = studentSts;
	}
	
	

}
