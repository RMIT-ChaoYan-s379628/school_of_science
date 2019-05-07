package com.rmit.engine.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.rmit.main.library.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rmit.engine.inf.IFacultyControls;
import com.rmit.engine.utils.NotificationClient;
import com.rmit.main.library.dto.AddContactsDTO;
import com.rmit.main.library.dto.AddDeadlineEventDTO;
import com.rmit.main.library.dto.AddEventDTO;
import com.rmit.main.library.dto.AddNewsDTO;
import com.rmit.main.library.enums.Category;
import com.rmit.main.library.enums.Department;
import com.rmit.main.library.enums.UserStatus;
import com.rmit.main.library.feed.model.Contacts;
import com.rmit.main.library.feed.model.Feed;
import com.rmit.main.library.feed.model.User;
import com.rmit.main.library.repository.AUserRepository;
import com.rmit.main.library.repository.ContactsRepository;
import com.rmit.main.library.repository.FeedRepository;

@Service
public class FacultyControlsImpl implements IFacultyControls {

	Logger logger = LoggerFactory.getLogger(FacultyControlsImpl.class);

	@Autowired
	FeedRepository feedRepo;

	@Autowired
	ContactsRepository contactRepo;

	@Autowired
	AUserRepository userRepo;

	@Autowired
	NotificationClient notificationClient;

	@Override
	public ServiceResponse addFeed(AddDeadlineEventDTO feed) throws JsonProcessingException {

		ServiceResponse response = new ServiceResponse();
		Feed feedBo = new Feed();
		feedBo.setFeedId("feed_" + UUID.randomUUID().toString());
		feedBo.setAuthorId(feed.getAuthorId());
		feedBo.setCategory(Category.DEADLINES);
		feedBo.setCreatedDate(new Date());
		feedBo.setDeadlineDate(feed.getDeadlineDate());
		feedBo.setDeleted(false);
		feedBo.setUpdatedDate(new Date());
		feedBo.setDepartment(Department.valueOf(feed.getDepartment().toUpperCase()));
		feedBo.setNews(feed.getNews());
		feedBo.setTitle(feed.getTitle());
		feedBo.setImageurl(feed.getImageurl());
		feedBo.setSendNotification(feed.isSendNotification());
		if (feed.isSendNotification()) {

			try {
				
					notificationClient.pushNotificationToAll(feedBo.getTitle(), feedBo.getCategory().toString());
				
			} catch (Exception e) {
				logger.info("error while sending notifications");
			}
		}
		feedRepo.save(feedBo);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;
	}

	@Override
	public ServiceResponse addFeed(AddEventDTO feed) throws JsonProcessingException {

		ServiceResponse response = new ServiceResponse();
		Feed feedBo = new Feed();
		feedBo.setFeedId("feed_" + UUID.randomUUID().toString());
		feedBo.setAuthorId(feed.getAuthorId());
		feedBo.setTitle(feed.getTitle());
		feedBo.setDepartment(Department.valueOf(feed.getDepartment().toUpperCase()));
		feedBo.setCategory(Category.EVENTS);
		feedBo.setCreatedDate(new Date());
		feedBo.setUpdatedDate(new Date());
		feedBo.setDeadlineDate(feed.getDeadlineDate());
		feedBo.setDeleted(false);
		feedBo.setImageurl(feed.getImageurl());
		feedBo.setEventTagline(feed.getEventTagline());
		feedBo.setSendNotification(feed.isSendNotification());
		if (feed.isSendNotification()) {
			try {
				
					notificationClient.pushNotificationToAll(feedBo.getTitle(), feedBo.getCategory().toString());
				
			} catch (Exception e) {
				logger.info("error while sending notifications");
			}
		}
		feedRepo.save(feedBo);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;

	}

	@Override
	public ServiceResponse addFeed(AddNewsDTO feed) throws JsonProcessingException {
		ServiceResponse response = new ServiceResponse();
		Feed feedBo = new Feed();
		feedBo.setFeedId("feed_" + UUID.randomUUID().toString());
		feedBo.setAuthorId(feed.getAuthorId());
		feedBo.setCategory(Category.NEWS);
		feedBo.setCreatedDate(new Date());
		feedBo.setUpdatedDate(new Date());
		feedBo.setNews(feed.getNews());
		feedBo.setDeleted(false);
		feedBo.setSendNotification(feed.isSendNotification());
		feedBo.setDepartment(Department.valueOf(feed.getDepartment().toUpperCase()));
		feedBo.setTitle(feed.getTitle());
		feedBo.setImageurl(feed.getImageurl());
		if (feed.isSendNotification()) {

			try {
				
					notificationClient.pushNotificationToAll(feedBo.getTitle(), feedBo.getCategory().toString());
				
			} catch (Exception e) {
				logger.info("error while sending notifications");
			}
		}
		feedRepo.save(feedBo);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

		return response;
	}

	@Override
	public ServiceResponse editFeed(Feed feed, FeedDTO feedNew) throws JsonProcessingException {
		ServiceResponse response = new ServiceResponse();

		switch (Category.valueOf(feedNew.getCategory().toUpperCase())) {
		case DEADLINES:
			feed.setUpdatedDate(new Date());
			feed.setDeadlineDate(feedNew.getDeadlineDate());
			feed.setDepartment(Department.valueOf(feedNew.getDepartment().toUpperCase()));
			feed.setTitle(feedNew.getTitle());
			feed.setNews(feedNew.getNews());
			feed.setSendNotification(feedNew.isSendNotification());
			feed.setImageurl(feedNew.getImageurl());
			if (feed.isSendNotification()) {
				try {
					
						notificationClient.pushNotificationToAll(feed.getTitle(), feed.getCategory().toString());
					
				} catch (Exception e) {
					logger.info("error while sending notifications");
				}
			}

			feedRepo.save(feed);
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
			break;
		case EVENTS:
			feed.setUpdatedDate(new Date());
			feed.setTitle(feedNew.getTitle());
			feed.setDepartment(Department.valueOf(feedNew.getDepartment().toUpperCase()));
			feed.setDeadlineDate(feedNew.getDeadlineDate());
			feed.setEventTagline(feedNew.getEventTagline());
			feed.setSendNotification(feedNew.isSendNotification());
			feed.setImageurl(feedNew.getImageurl());
			if (feed.isSendNotification()) {
				try {
					
						notificationClient.pushNotificationToAll(feed.getTitle(), feed.getCategory().toString());
					
				} catch (Exception e) {
					logger.info("error while sending notifications");
				}
			}
			feedRepo.save(feed);
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
			break;
		case NEWS:
			feed.setUpdatedDate(new Date());
			feed.setTitle(feedNew.getTitle());
			feed.setNews(feedNew.getNews());
			feed.setDepartment(Department.valueOf(feedNew.getDepartment().toUpperCase()));
			feed.setSendNotification(feedNew.isSendNotification());
			feed.setImageurl(feedNew.getImageurl());
			if (feed.isSendNotification()) {
				try {
					
						notificationClient.pushNotificationToAll(feed.getTitle(), feed.getCategory().toString());
					
				} catch (Exception e) {
					logger.info("error while sending notifications");
				}
			}
			feedRepo.save(feed);
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
			break;
		default:
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			break;

		}

		return response;
	}

	@Override
	public ServiceResponse deleteFeed(Feed feed) {
		ServiceResponse response = new ServiceResponse();

		feed.setDeleted(true);
		feed.setUpdatedDate(new Date());
		feedRepo.save(feed);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

		return response;
	}

	@Override
	public ServiceResponse addContact(AddContactsDTO contact) {

		ServiceResponse response = new ServiceResponse();

		Contacts con = new Contacts();
		con.setDeleted(false);
		con.setContactId("contact_" + UUID.randomUUID().toString());
		con.setDepartment(Department.valueOf(contact.getDepartment().toUpperCase()));
		con.setEmailId(contact.getEmailId());
		con.setName(contact.getName());
		con.setPhoneNo(contact.getPhoneNo());
		con.setCreatedDate(new Date());
		con.setUpdatedDate(new Date());
		contactRepo.save(con);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;

	}

	@Override
	public ServiceResponse editContact(Contacts contact, ContactDTO contactNew) {

		ServiceResponse response = new ServiceResponse();
		contact.setDepartment(Department.valueOf(contactNew.getDepartment().toUpperCase()));
		contact.setEmailId(contactNew.getEmailId());
		contact.setName(contactNew.getName());
		contact.setPhoneNo(contactNew.getPhoneNo());
		contact.setUpdatedDate(new Date());
		contactRepo.save(contact);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;

	}

	@Override
	public ServiceResponse deleteContact(Contacts contact) {

		ServiceResponse response = new ServiceResponse();

		contact.setDeleted(true);
		contact.setUpdatedDate(new Date());
		contactRepo.save(contact);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

		return response;

	}

	@Override
	public ServiceResponse addUsers(String userId, AddUsersRequest request) {

		ServiceResponse response = new ServiceResponse();
		for (AddUserDTO userDTO : request.getUsers()) {
			User user = new User();
			BeanUtils.copyProperties(userDTO, user);
			user.setDepartment(Department.valueOf(userDTO.getDepartment().toUpperCase()));
			user.setStatus(UserStatus.UNVERIFIED);
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			userRepo.save(user);

		}
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;

	}

	@Override
	public DisableUsersResponse disableUsers(String clientUserId, DisableUsersRequest request) {
		DisableUsersResponse response = new DisableUsersResponse();

		logger.info("Fetched User : {}", Arrays.toString(request.getUserIds().toArray()));
		List<String> disabledUserIds = new ArrayList<>();
		for (String userId : request.getUserIds()) {
			User user = userRepo.findOneByUserIdAndStatus(userId, UserStatus.ACTIVE);
			if (user != null) {
				ServiceResponse resp = notificationClient.unregister(user.getDepartment().toString(), "enginecall",
						user.getUserId());
				if (resp.getCode() != 0) {
					logger.info("user  notification not disabled since notification service is not running");
				}
				user.setStatus(UserStatus.DISABLED);
				user.setUpdatedDate(new Date());
				userRepo.save(user);
				disabledUserIds.add(userId);
			}
		}
		response.setDisabledUserIds(disabledUserIds);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;
	}

	@Override
	public ServiceResponse getUsers(String userId, int index, int size) {
		GetUserResponse response = new GetUserResponse();

		if (size == 0) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage("Size cannot be 0");
			return response;
		}
		Pageable pageable = new PageRequest(index, size);
		Page<User> userBo;
		userBo = userRepo.findAllByOrderByCreatedDateDesc(pageable);
		if (userBo == null || !userBo.hasContent()) {
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage("Feed is Empty");
			return response;
		}
		long totalSize = getUserSize();
		List<UserDTO> userList = convertToUserDTO(userBo);
		response.setUserList(userList);
		response.setSize(totalSize);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

		return response;
	}

	private long getUserSize() {

		long count = 0;
		count = userRepo.count();
		return count;
	}

	private List<UserDTO> convertToUserDTO(Page<User> userBo) {

		List<UserDTO> userList = new ArrayList<>();
		for (User u : userBo.getContent()) {
			UserDTO user = new UserDTO();
			BeanUtils.copyProperties(u, user);
			user.setDepartment(u.getDepartment().getValue());
			user.setStatus(u.getStatus().getValue());
			userList.add(user);
		}
		return userList;
	}

	@Override
	public ServiceResponse searchUser(String userId, String user) {

		GetUserResponse response = new GetUserResponse();
		List<UserDTO> userList = new ArrayList<>();
		if (user == null || user == "") {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage("User String cannot be null");
			return response;
		}
		User u = userRepo.findOneByUserId(user);
		if (u == null) {
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage("User not present");
			return response;
		}

		UserDTO ud = new UserDTO();
		BeanUtils.copyProperties(u, ud);
		ud.setDepartment(u.getDepartment().getValue());
		ud.setStatus(u.getStatus().getValue());
		userList.add(ud);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		response.setUserList(userList);
		response.setSize(1);

		return response;

	}


}
