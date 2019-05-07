package com.rmit.engine.inf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rmit.main.library.api.AddUsersRequest;
import com.rmit.main.library.api.ContactDTO;
import com.rmit.main.library.api.DisableUsersRequest;
import com.rmit.main.library.api.DisableUsersResponse;
import com.rmit.main.library.api.FeedDTO;
import com.rmit.main.library.api.ServiceResponse;
import com.rmit.main.library.dto.AddContactsDTO;
import com.rmit.main.library.dto.AddDeadlineEventDTO;
import com.rmit.main.library.dto.AddEventDTO;
import com.rmit.main.library.dto.AddNewsDTO;
import com.rmit.main.library.feed.model.Contacts;
import com.rmit.main.library.feed.model.Feed;

public interface IFacultyControls {
	/*
	 * 1. Add Feed(news,events,deadlines) 2. Remove Feed(news,events,deadlines) 3.
	 * Edit Feed(news,events,deadlines) 4. Add Contacts 5. Remove Contacts 6. Edit
	 * Contacts
	 */

	public ServiceResponse addFeed(AddDeadlineEventDTO feed) throws JsonProcessingException;

	public ServiceResponse addFeed(AddEventDTO feed) throws JsonProcessingException;

	public ServiceResponse addFeed(AddNewsDTO feed) throws JsonProcessingException;

	public ServiceResponse editFeed(Feed feedOld, FeedDTO feedNew) throws JsonProcessingException;

	public ServiceResponse deleteFeed(Feed feed);

	public ServiceResponse addContact(AddContactsDTO contact);

	public ServiceResponse editContact(Contacts contactOld,ContactDTO contactNew);

	public ServiceResponse deleteContact(Contacts contact);
	
	public ServiceResponse addUsers(String userId, AddUsersRequest request);
	
	public DisableUsersResponse disableUsers(String userId, DisableUsersRequest request);
	
	public ServiceResponse getUsers(String userId,int index, int size);
	
	public ServiceResponse searchUser(String userId,String user);

}
