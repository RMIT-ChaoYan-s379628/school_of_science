package com.rmit.main.library.api;

import java.util.List;

public class DisableUsersResponse extends ServiceResponse {

	private static final long serialVersionUID = 7853108466156063353L;
	
	private List<String> disabledUserIds;

	public List<String> getDisabledUserIds() {
		return disabledUserIds;
	}

	public void setDisabledUserIds(List<String> disabledUserIds) {
		this.disabledUserIds = disabledUserIds;
	}

}
