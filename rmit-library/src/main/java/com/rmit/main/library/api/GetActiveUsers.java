package com.rmit.main.library.api;

public class GetActiveUsers extends ServiceResponse {

	private static final long serialVersionUID = -746375234088399690L;
	
	private long active;

	public long getActive() {
		return active;
	}

	public void setActive(long active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "GetActiveUsers [active=" + active + "]";
	}
	
	

}
