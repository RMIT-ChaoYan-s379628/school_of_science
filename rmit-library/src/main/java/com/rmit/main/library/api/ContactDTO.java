package com.rmit.main.library.api;

import java.io.Serializable;
import java.util.Date;

public class ContactDTO implements Serializable {

	private static final long serialVersionUID = -972959143838304963L;

	private String contactId;

	private String emailId;

	private String name;

	private String phoneNo;

	private Date createdDate;

	private Date updatedDate;

	private String department;

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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ContactDTO [contactId=" + contactId + ", emailId=" + emailId + ", name=" + name + ", phoneNo=" + phoneNo
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", department=" + department
				+ ", isDeleted=" + isDeleted + "]";
	}

	
}
