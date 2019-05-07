package com.rmit.main.library.enums;

public enum Department {
	
	LEARNING("LEARNING"),
	RESEARCH("RESEARCH"),
	GENERAL("GENERAL"),
	ALL("ALL");
	
	private String value;
	
	public String getValue() {
		return this.value;
	}
	
	private Department(String value) {
		this.value = value;
	}

}
