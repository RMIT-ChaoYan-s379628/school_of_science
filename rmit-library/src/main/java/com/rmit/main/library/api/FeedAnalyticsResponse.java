package com.rmit.main.library.api;

import java.util.Map;

public class FeedAnalyticsResponse extends ServiceResponse{

	private static final long serialVersionUID = 316822001297149381L;
	
	private Map<String, Long> data ;

	public Map<String, Long> getData() {
		return data;
	}

	public void setData(Map<String, Long> data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FeedAnalyticsResponse [data=" + data + "]";
	}
	
	

}
