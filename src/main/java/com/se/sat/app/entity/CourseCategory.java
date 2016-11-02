package com.se.sat.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="course_category")
public class CourseCategory {
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="CODE_NAME")
	private String codeName;
	
	@Column(name="NAME")
	private String name;
	
	private CourseCategory superCourseCategory;

	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CourseCategory getSuperCourseCategory() {
		return superCourseCategory;
	}

	public void setSuperCourseCategory(CourseCategory superCourseCategory) {
		this.superCourseCategory = superCourseCategory;
	}
	
}
