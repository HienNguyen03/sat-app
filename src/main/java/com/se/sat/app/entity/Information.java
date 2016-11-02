package com.se.sat.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="information")
public class Information {
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="APPLIED_DATE")
	private Date appliedDate;
	
	@Column(name="ACTIVATED_DURATION")
	private int activatedDuration;
	
	@Column(name="ATTEMPTS")
	private int attempts;
	
	@Column(name="VALID_REGISTERED_DURATION")
	private int validRegisteredDuration;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public int getActivatedDuration() {
		return activatedDuration;
	}

	public void setActivatedDuration(int activatedDuration) {
		this.activatedDuration = activatedDuration;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public int getValidRegisteredDuration() {
		return validRegisteredDuration;
	}

	public void setValidRegisteredDuration(int validRegisteredDuration) {
		this.validRegisteredDuration = validRegisteredDuration;
	}
	
}
