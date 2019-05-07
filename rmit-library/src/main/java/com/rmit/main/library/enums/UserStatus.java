package com.rmit.main.library.enums;

public enum UserStatus {
	
	ACTIVE("ACTIVE"),
	SUSPENDED("SUSPENDED"),
	UNVERIFIED("UNVERIFIED"),
	DISABLED("DISABLED");
	
	private String value;
	
	public String getValue() {
		return this.value;
	}
	
	private UserStatus(String value) {
		this.value = value;
	}
	
}
