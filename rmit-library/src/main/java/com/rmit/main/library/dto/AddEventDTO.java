package com.rmit.main.library.dto;

import java.util.Date;

public class AddEventDTO {


	private String authorId;

	private String title;

	private String department;

	private Date deadlineDate;

	private String eventTagline;

	private boolean sendNotification;

	private boolean isDeleted;
	
	private String imageurl;
	
	

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getEventTagline() {
		return eventTagline;
	}

	public void setEventTagline(String eventTagline) {
		this.eventTagline = eventTagline;
	}

	public boolean isSendNotification() {
		return sendNotification;
	}

	public void setSendNotification(boolean sendNotification) {
		this.sendNotification = sendNotification;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "AddEventDTO [authorId=" + authorId + ", title=" + title + ", department=" + department
				+ ", deadlineDate=" + deadlineDate + ", eventTagline=" + eventTagline + ", sendNotification="
				+ sendNotification + ", isDeleted=" + isDeleted + ", imageurl=" + imageurl + "]";
	}

	
	
	

}
