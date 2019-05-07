package com.rmit.main.library.enums;

public enum Category {
	
	NEWS("NEWS"),
	EVENTS("EVENTS"),
	DEADLINES("DEADLINES");
	
	private String value;
	
	public String getValue() {
		return this.value;
	}
	
	private Category(String value) {
		this.value=value;
	}

}
