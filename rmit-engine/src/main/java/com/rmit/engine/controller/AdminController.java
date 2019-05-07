package com.rmit.engine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmit.engine.inf.IFacultyControls;
import com.rmit.main.library.api.AddUsersRequest;
import com.rmit.main.library.api.ApiRequestUtils;
import com.rmit.main.library.api.ApiResponseUtils;
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
import com.rmit.main.library.repository.ContactsRepository;
import com.rmit.main.library.repository.FeedRepository;

@RestController()
@RequestMapping(value = "/faculty")
public class AdminController {

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	IFacultyControls facultyImpl;

	@Autowired
	FeedRepository frepo;

	@Autowired
	ContactsRepository conRepo;

	@RequestMapping(value = "/addNews", method = RequestMethod.POST)
	public ServiceResponse addNews(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody AddNewsDTO request) {

		ServiceResponse response = new ServiceResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			response = facultyImpl.addFeed(request);
		} catch (Exception e) {
			logger.error("Some error occured while adding News");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public ServiceResponse addEvent(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody AddEventDTO request) {

		ServiceResponse response = new ServiceResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			response = facultyImpl.addFeed(request);
		} catch (Exception e) {
			logger.error("Some error occured while adding Event");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "/addDeadLine", method = RequestMethod.POST)
	public ServiceResponse addDeadLine(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody AddDeadlineEventDTO request) {

		ServiceResponse response = new ServiceResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			response = facultyImpl.addFeed(request);
		} catch (Exception e) {
			logger.error("Some error occured while adding Deadline");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "/editFeed", method = RequestMethod.POST)
	public ServiceResponse editNews(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody FeedDTO request) {

		ServiceResponse response = new ServiceResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			Feed feedOld = frepo.findOneByFeedId(request.getFeedId());
			response = facultyImpl.editFeed(feedOld, request);
		} catch (Exception e) {
			logger.error("Some error occured while Editing Feed");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}

		return response;

	}

	@RequestMapping(value = "/deleteFeed", method = RequestMethod.POST)
	public ServiceResponse deleteFeed(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody String request) {

		ServiceResponse response = new ServiceResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			Feed feed = frepo.findOneByFeedId(request);
			response = facultyImpl.deleteFeed(feed);
		} catch (Exception e) {
			logger.error("Some error occured while Deleting feed");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}

		return response;

	}

	@RequestMapping(value = "/addContact", method = RequestMethod.POST)
	public ServiceResponse addContact(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody AddContactsDTO request) {

		ServiceResponse response = new ServiceResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			response = facultyImpl.addContact(request);
		} catch (Exception e) {
			logger.error("Some error occured while adding Contact");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}

		return response;

	}

	@RequestMapping(value = "/editContact", method = RequestMethod.POST)
	public ServiceResponse editContact(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody ContactDTO request) {

		ServiceResponse response = new ServiceResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			Contacts con = conRepo.findOneByContactId(request.getContactId());
			response = facultyImpl.editContact(con, request);
		} catch (Exception e) {
			logger.error("Some error occured while Editing Contact");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}

		return response;

	}

	@RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
	public ServiceResponse deleteContact(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody String request) {

		ServiceResponse response = new ServiceResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			Contacts con = conRepo.findOneByContactId(request);
			response = facultyImpl.deleteContact(con);
		} catch (Exception e) {
			logger.error("Some error occured while Deleting Contact");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}

		return response;

	}

	@RequestMapping(value = "/addUsers", method = RequestMethod.POST)
	public ServiceResponse addUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody AddUsersRequest request) {
		ServiceResponse response = new ServiceResponse();
		if (request == null || !request.isValid()) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			response = facultyImpl.addUsers(userId, request);
		} catch (Exception e) {
			logger.error("Some error occured while adding users");
			logger.error(ApiResponseUtils.EXCEPTION, e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/disableClient", method = RequestMethod.POST)
	public DisableUsersResponse disableClientUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody DisableUsersRequest request) {
		DisableUsersResponse response = new DisableUsersResponse();
		if (request == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			response = facultyImpl.disableUsers(userId, request);
		} catch (Exception e) {
			logger.error("Some error occurred while disabling users");
			logger.error(ApiResponseUtils.EXCEPTION, e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public ServiceResponse getUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestParam(value = ApiRequestUtils.INDEX, required = true) int index,
			@RequestParam(value = ApiRequestUtils.SIZE, required = true) int size) {
		ServiceResponse response = new ServiceResponse();
		try {
			response = facultyImpl.getUsers(userId, index, size);
		} catch (Exception e) {
			logger.error("Some error occurred while disabling users");
			logger.error(ApiResponseUtils.EXCEPTION, e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/searchUser", method = RequestMethod.GET)
	public ServiceResponse searchUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestParam(value = "user", required = true) String user) {
		ServiceResponse response = new ServiceResponse();
		try {
			response = facultyImpl.searchUser(userId, user);
		} catch (Exception e) {
			logger.error("Some error occurred while disabling users");
			logger.error(ApiResponseUtils.EXCEPTION, e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}
		return response;
	}



}
