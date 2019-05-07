package com.rmit.main.library.api;

import java.io.Serializable;
import java.util.Date;

public class FacultyDTO implements Serializable {

	private static final long serialVersionUID = -6271290151414042777L;

	private String userId;

	private String name;

	private String department;

	private Date createdDate;

	private Date updatedDate;

	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FacultyDTO [userId=" + userId + ", name=" + name + ", department=" + department + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", status=" + status + "]";
	}

}
