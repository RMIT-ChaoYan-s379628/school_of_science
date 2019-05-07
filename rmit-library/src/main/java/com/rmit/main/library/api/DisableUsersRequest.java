package com.rmit.main.library.api;

import java.util.List;

public class DisableUsersRequest extends ServiceRequest {

	private static final long serialVersionUID = -6261504754563352619L;
	
	private List<String> userIds;

	@Override
	public boolean isValid() {
		if(userIds == null || userIds.isEmpty())
			return false;
		return true;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

}
