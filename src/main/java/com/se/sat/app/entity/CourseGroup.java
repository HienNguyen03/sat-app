package com.se.sat.app.entity;

import java.util.ArrayList;
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
@Table(name = "course_group")
public class CourseGroup {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "CODE_NAME")
	private String codeName;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "enrollment", joinColumns = @JoinColumn(name = "COURSE_GROUP_ID"), inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
	private List<Student> students = new ArrayList<Student>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "group_session", joinColumns = @JoinColumn(name = "COURSE_GROUP_ID"), inverseJoinColumns = @JoinColumn(name = "STUDY_SESSION_ID"))
	private List<StudySession> studySessions = new ArrayList<StudySession>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSE_ID")
	private Course course;

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

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<StudySession> getStudySessions() {
		return studySessions;
	}

	public void setStudySessions(List<StudySession> studySessions) {
		this.studySessions = studySessions;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	

}
