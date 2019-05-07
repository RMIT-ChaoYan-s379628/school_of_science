package com.rmit.main.library.feed.model;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="CalenderEvents")
public class CalenderEvents {
	
	@Id
	private String Id;
	
	private Map<String,Date> calender;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Map<String, Date> getCalender() {
		return calender;
	}

	public void setCalender(Map<String, Date> calender) {
		this.calender = calender;
	}

	@Override
	public String toString() {
		return "CalenderEvents [Id=" + Id + ", calender=" + calender + "]";
	}
	
}
