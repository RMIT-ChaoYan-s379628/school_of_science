package com.rmit.engine.inf;

import java.util.List;
import java.util.Set;

import com.rmit.main.library.api.*;
import com.rmit.main.library.enums.Category;
import com.rmit.main.library.enums.Department;
import com.rmit.main.library.exception.FeignCallException;

import com.rmit.main.library.api.FeedDTO;
import com.rmit.main.library.feed.model.Feed;

public interface IStudentControls {

	/*
	 * 1. view feed(news,events,deadlines) 2. view contacts
	 */

	GetFeedResponseDTO getFeedforMobile(String userId, Set<String> department, String type);

	GetFeedResponseDTO getFeed(String userId, Set<String> department,String type, int index, int size);

	GetContactResponseDTO getContact(String userId, Set<String> department, int index, int size);
	
	GetCalenderResponse getCalender(String userId);
	
	ServiceResponse shareFeed(String userId,String feedId) throws FeignCallException;

}
