package com.se.sat.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "session_category")
public class SessionCategory {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "NAME")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sessionCategory")
	private List<StudySession> studySessions = new ArrayList<StudySession>();

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

	public List<StudySession> getStudySessions() {
		return studySessions;
	}

	public void setStudySessions(List<StudySession> studySessions) {
		this.studySessions = studySessions;
	}
	
	

}
