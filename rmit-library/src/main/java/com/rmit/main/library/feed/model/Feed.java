package com.rmit.main.library.feed.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rmit.main.library.enums.Department;
import com.rmit.main.library.enums.Category;

@Document(collection="feed")
public class Feed implements Serializable {
	
	/*
	 * feed can be of : news, events,duedates.
	 */

	private static final long serialVersionUID = -8611553777039177617L;
	
	@Id
	private String Id;
	
	private String feedId;
	
	private Date createdDate;
	
	private String news;
	
	private String authorId;
	
	private String title;
	
	private Department department;
	
	private Date updatedDate;
	
	private Category category;
	
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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	@Override
	public String toString() {
		return "Feed [Id=" + Id + ", feedId=" + feedId + ", createdDate=" + createdDate + ", news=" + news
				+ ", authorId=" + authorId + ", title=" + title + ", department=" + department + ", updatedDate="
				+ updatedDate + ", category=" + category + ", deadlineDate=" + deadlineDate + ", eventTagline="
				+ eventTagline + ", sendNotification=" + sendNotification + ", isDeleted=" + isDeleted + ", imageurl="
				+ imageurl + "]";
	}
}
