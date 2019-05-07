package com.rmit.mailer.library.request;

public class WelcomeMessageRequest extends ServiceRequest{

	private static final long serialVersionUID = 7596672143229164158L;
	private String to;
	
	@Override
	public boolean isValid() {
		return false;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
}
