package com.rmit.main.library.api;

import java.io.Serializable;

public class AddUserDTO implements Serializable{

	private static final long serialVersionUID = -9198338638322060714L;
	
	private String userId;
	
	private String name;
	
	private String department;
	
	public boolean isValid() {
		if(userId == null || userId.isEmpty())
			return false;
		if(name == null || name.isEmpty())
			return false;
		return true;
	}

	
	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
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

}
