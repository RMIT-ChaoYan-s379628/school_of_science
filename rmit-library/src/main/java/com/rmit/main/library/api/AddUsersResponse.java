package com.rmit.main.library.api;

import java.util.Map;

public class AddUsersResponse extends ServiceResponse{

	private static final long serialVersionUID = 8644884674490578411L;
	
	Map<String, Boolean> inviteStatus;

	public Map<String, Boolean> getInviteStatus() {
		return inviteStatus;
	}

	public void setInviteStatus(Map<String, Boolean> inviteStatus) {
		this.inviteStatus = inviteStatus;
	}
}
