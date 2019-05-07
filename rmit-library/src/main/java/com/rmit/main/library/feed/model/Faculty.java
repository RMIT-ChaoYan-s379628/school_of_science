package com.rmit.main.library.feed.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rmit.main.library.enums.Department;
import com.rmit.main.library.enums.UserStatus;

@Document(collection="faculty")
public class Faculty implements Serializable{

	private static final long serialVersionUID = 513497015772811094L;
	
	@Id
	private String id;
	
	private String userId;
	
	private String name;
	 
	private Department department;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private UserStatus status;

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Faculty [id=" + id + ", userId=" + userId + ", name=" + name + ", department=" + department
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}
	
	


}
