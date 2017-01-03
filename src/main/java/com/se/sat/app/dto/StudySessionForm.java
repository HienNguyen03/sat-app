package com.se.sat.app.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudySessionForm {

	@NotNull
	@Size(min = 1, message = "{Size.courseForm.name}")
	private String name;

	@NotNull
	private Date startTime;

	@NotNull
	private Date endTime;

	@NotNull
	private Date sessionDate;

	@NotNull
	@Size(min = 4, message = "{Size.studySessionForm.password}")
	private String password;

	@NotNull
	private String sessionCategory;

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
		return "StudySessionForm [name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + ", sessionDate="
				+ sessionDate + ", password=" + password + ", sessionCategory=" + sessionCategory + "]";
	}

}
