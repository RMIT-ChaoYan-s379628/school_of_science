package com.rmit.main.library.dto;

public class AddContactsDTO {
	
	private String emailId;
	
	private String name;
	
	private String phoneNo;
	
	private String department;
	
	private boolean isDeleted;

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

	@Override
	public String toString() {
		return "AddContactsDTO [emailId=" + emailId + ", name=" + name + ", phoneNo=" + phoneNo + ", department="
				+ department + ", isDeleted=" + isDeleted + "]";
	} 
	
	

}
