package com.rmit.main.library.feed.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rmit.main.library.enums.Department;

@Document(collection="contacts")
public class Contacts implements Serializable{

	private static final long serialVersionUID = 8397739023293432197L;
	
	@Id
	private String id;
	
	private String contactId;
	
	private String emailId;
	
	private String name;
	
	private String phoneNo;
	
	private Date createdDate;
	
	private Date updatedDate;
	
	private Department department;
	
	private boolean isDeleted; 
	
	
	
	

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

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Contacts [id=" + id + ", contactId=" + contactId + ", emailId=" + emailId + ", name=" + name
				+ ", phoneNo=" + phoneNo + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", department=" + department + ", isDeleted=" + isDeleted + "]";
	}

	

	

}
