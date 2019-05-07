package com.rmit.main.library.api;

import java.util.Date;
import java.util.Map;

public class GetCalenderResponse extends ServiceResponse{

	private static final long serialVersionUID = 7220484241075488133L;
	
	Map<String,Date> calender;

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
		return "GetCalenderResponse [calender=" + calender + "]";
	}
	
	

}
