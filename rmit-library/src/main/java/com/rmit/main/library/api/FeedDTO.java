package com.rmit.main.library.api;

import java.io.Serializable;
import java.util.Date;

public class FeedDTO implements Serializable {

	private static final long serialVersionUID = -9215392159438355438L;

	private String feedId;

	private Date createdDate;

	private String news;

	private String authorId;

	private String title;

	private String department;

	private Date updatedDate;

	private String category;

	private Date deadlineDate;

	private String eventTagline;

	private boolean sendNotification;
	
	private String imageurl;

	private boolean isDeleted;
	
	

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
		return "FeedDTO [feedId=" + feedId + ", createdDate=" + createdDate + ", news=" + news + ", authorId="
				+ authorId + ", title=" + title + ", department=" + department + ", updatedDate=" + updatedDate
				+ ", category=" + category + ", deadlineDate=" + deadlineDate + ", eventTagline=" + eventTagline
				+ ", sendNotification=" + sendNotification + ", imageurl=" + imageurl + ", isDeleted=" + isDeleted
				+ "]";
	}


}
