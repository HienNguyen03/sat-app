package com.se.sat.app.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.se.sat.app.entity.Course;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.entity.User;

public class CourseForm {

	@NotNull
	@Size(min = 1, message = "{Size.courseForm.name}")
	private String name;

	@NotNull
	@Size(min = 4, message = "{Size.courseForm.description}")
	private String description;

	@NotNull
	private Date startDate;

	@NotNull
	private Date endDate;

	@NotNull
	private Date startEnrollDate;

	@NotNull
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
