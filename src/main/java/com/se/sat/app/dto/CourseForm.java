package com.se.sat.app.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class CourseForm {

	@NotNull
	@Size(min = 1, message = "{Size.courseForm.name}")
	private String name;

	@NotNull
	@Size(min = 4, message = "{Size.courseForm.description}")
	private String description;

	@NotNull
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private Date startDate;

	@NotNull
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private Date endDate;

	@NotNull
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private Date startEnrollDate;

	@NotNull
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private Date endEnrollDate;

	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getStartEnrollDate() {
		return startEnrollDate;
	}

	public void setStartEnrollDate(Date startEnrollDate) {
		this.startEnrollDate = startEnrollDate;
	}

	public Date getEndEnrollDate() {
		return endEnrollDate;
	}

	public void setEndEnrollDate(Date endEnrollDate) {
		this.endEnrollDate = endEnrollDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CourseForm [name=" + name + ", description=" + description + ", startDate=" + startDate + ", endDate="
				+ endDate + ", startEnrollDate=" + startEnrollDate + ", endEnrollDate=" + endEnrollDate + ", status="
				+ status + "]";
	}

}
