package com.rmit.main.library.dto;

public class AddNewsDTO {
	
	
	private String news;
	
	private String authorId;
	
	private String title;
	
	private String department;
	
	private boolean sendNotification;
	
	private boolean isDeleted;
	
	private String imageurl;
	
	

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
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
		return "AddNewsDTO [news=" + news + ", authorId=" + authorId + ", title=" + title + ", department=" + department
				+ ", sendNotification=" + sendNotification + ", isDeleted=" + isDeleted + ", imageurl=" + imageurl
				+ "]";
	}

}
