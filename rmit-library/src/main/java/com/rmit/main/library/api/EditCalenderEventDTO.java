package com.rmit.main.library.api;

import java.io.Serializable;
import java.util.Date;

public class EditCalenderEventDTO implements Serializable{
	
	private static final long serialVersionUID = -5346451413694602883L;

	private String event;
	
	private Date Date;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	@Override
	public String toString() {
		return "EditCalenderEventDTO [event=" + event + ", Date=" + Date + "]";
	}
	
	

}
