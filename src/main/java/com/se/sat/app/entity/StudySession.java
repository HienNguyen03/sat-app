package com.se.sat.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "study_session")
public class StudySession implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID")
	private Course course;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "paticipation", joinColumns = @JoinColumn(name = "STUDY_SESSION_ID"), inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
	private List<Student> studentPas = new ArrayList<Student>();

	@Column(name = "NAME")
	private String name;

	@Temporal(TemporalType.TIME)
	@Column(name = "START_TIME")
	private Date startTime;

	@Temporal(TemporalType.TIME)
	@Column(name = "END_TIME")
	private Date endTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "SESSION_DATE")
	private Date sessionDate;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "SESSION_CATEGORY")
	private String sessionCategory;

	public StudySession() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Student> getStudentPas() {
		return studentPas;
	}

	public void setStudentPas(List<Student> studentPas) {
		this.studentPas = studentPas;
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

	public String getSessionCategory() {
		return sessionCategory;
	}

	public void setSessionCategory(String sessionCategory) {
		this.sessionCategory = sessionCategory;
	}

	@Override
	public String toString() {
		return "StudySession [id=" + id + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", sessionDate=" + sessionDate + ", password=" + password + ", sessionCategory=" + sessionCategory
				+ "]";
	};

}
