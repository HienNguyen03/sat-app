package com.se.sat.app.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class EditStudySessionForm {

	@NotNull
	@Size(min = 1, message = "{Size.studySessionForm.name}")
	private String name;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private Date startTime;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private Date endTime;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date sessionDate;

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

	public String getSessionCategory() {
		return sessionCategory;
	}

	public void setSessionCategory(String sessionCategory) {
		this.sessionCategory = sessionCategory;
	}

	@Override
	public String toString() {
		return "EditStudySessionForm [name=" + name + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", sessionDate=" + sessionDate + ", sessionCategory=" + sessionCategory + "]";
	}

}
