
package com.rmit.main.library.api;

public class AddClientRequest extends ServiceRequest {

    private static final long serialVersionUID = -3168964205149359434L;
    
    private String name;
    
    private String userId;
    
    private String department;
    
    
	@Override
	public boolean isValid() {
		if(name == null || name.isEmpty())
			return false;
		if(userId == null || userId.isEmpty())
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	

}
