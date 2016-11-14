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
@Table(name = "department")
public class Department {

	@Id
	@Column(name = "ID")
	private int id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
	private List<Teacher> teachers = new ArrayList<Teacher>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
	private List<AcademicGroup> academicGroups = new ArrayList<AcademicGroup>();

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "LANGUAGE")
	private String language;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "superDepartment")
	private List<Department> departments = new ArrayList<Department>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department superDepartment;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Department getSuperDepartment() {
		return superDepartment;
	}

	public void setSuperDepartment(Department superDepartment) {
		this.superDepartment = superDepartment;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<AcademicGroup> getAcademicGroups() {
		return academicGroups;
	}

	public void setAcademicGroups(List<AcademicGroup> academicGroups) {
		this.academicGroups = academicGroups;
	}

}
