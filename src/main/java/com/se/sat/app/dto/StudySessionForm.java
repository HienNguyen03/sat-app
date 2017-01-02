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
	private String password;

	@NotNull
	private String sessionCategory;
}
