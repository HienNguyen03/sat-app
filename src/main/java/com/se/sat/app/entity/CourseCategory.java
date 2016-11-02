package com.se.sat.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "course_category")
public class CourseCategory {

	@Id
	@Column(name = "ID")
	private int id;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="courseCategory")
	private List<Course> courses = new ArrayList<Course>();

	@Column(name = "CODE_NAME")
	private String codeName;

	@Column(name = "NAME")
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="superCourseCategory")
	private List<CourseCategory> courseCategories = new ArrayList<CourseCategory>();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUPER_COURSE_CATEGORY_ID")
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

	public List<CourseCategory> getCourseCategories() {
		return courseCategories;
	}

	public void setCourseCategories(List<CourseCategory> courseCategories) {
		this.courseCategories = courseCategories;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
