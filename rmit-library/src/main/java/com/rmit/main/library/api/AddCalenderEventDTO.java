package com.rmit.main.library.api;

import java.util.Date;
import java.util.Map;

public class AddCalenderEventDTO extends ServiceRequest{

	private static final long serialVersionUID = 8559310332118174596L;
	
	private Map<String,Date> calender;
	
	
	public Map<String, Date> getCalender() {
		return calender;
	}


	public void setCalender(Map<String, Date> calender) {
		this.calender = calender;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AddCalenderEventDTO [calender=" + calender + "]";
	}


	@Override
	public boolean isValid() {
		if(!calender.isEmpty()) {
			return true;
		}
		return false;
	}

}
