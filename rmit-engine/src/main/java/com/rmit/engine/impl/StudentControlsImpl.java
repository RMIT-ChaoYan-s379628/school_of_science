package com.rmit.engine.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rmit.engine.inf.IStudentControls;
import com.rmit.engine.utils.MailerServiceClient;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.api.ContactDTO;
import com.rmit.main.library.api.FeedDTO;
import com.rmit.main.library.api.GetCalenderResponse;
import com.rmit.main.library.api.GetContactResponseDTO;
import com.rmit.main.library.api.GetFeedResponseDTO;
import com.rmit.main.library.api.ServiceResponse;
import com.rmit.main.library.enums.Category;
import com.rmit.main.library.enums.Department;
import com.rmit.main.library.exception.FeignCallException;
import com.rmit.main.library.feed.model.CalenderEvents;
import com.rmit.main.library.feed.model.Contacts;
import com.rmit.main.library.feed.model.Feed;
import com.rmit.main.library.mailer.model.SendVerificationMailRequest;
import com.rmit.main.library.repository.CalenderRepository;
import com.rmit.main.library.repository.ContactsRepository;
import com.rmit.main.library.repository.FeedRepository;

@Service
public class StudentControlsImpl implements IStudentControls {

	Logger logger = LoggerFactory.getLogger(StudentControlsImpl.class);

	@Autowired
	FeedRepository feedRepo;

	@Autowired
	ContactsRepository contactRepo;

	@Autowired
	CalenderRepository calRepo;

	@Autowired
	MailerServiceClient mailerClient;

	@Override
	public GetFeedResponseDTO getFeed(String userId, Set<String> department, String type, int index, int size) {

		GetFeedResponseDTO response = new GetFeedResponseDTO();

		if (size == 0) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage("Size cannot be 0");
			return response;
		}
		Set<Department> depart = getDepartmentFromPayload(department);
		long totalsize = getFeedSize(Category.valueOf(type.toUpperCase()), depart);
		logger.info("total size if : {}", totalsize);

		List<FeedDTO> feed = getFeed(Category.valueOf(type.toUpperCase()), depart, index, size);

		logger.info("feed fetched from Db are :{}", Arrays.toString(feed.toArray()));

		if (feed.isEmpty()) {
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage("Feed is Empty");
			return response;
		}
		response.setSize(totalsize);
		response.setFeed(feed);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;

	}

	private List<FeedDTO> getFeed(Category category, Set<Department> department, int index, int size) {
		Page<Feed> feedpage = null;
		Pageable pageable = new PageRequest(index, size);
		if (category != null) {
			if (department != null && !department.isEmpty()) {
				feedpage = feedRepo.findByCategoryAndDepartmentInOrderByCreatedDateDesc(category, department, pageable);
//				feedpage = feedRepo.findByCategoryAndDepartmentInAndIsDeletedOrderByCreatedDateDesc(category, department, false,pageable);
			} else {
				feedpage = feedRepo.findByCategoryOrderByCreatedDateDesc(category, pageable);
//				feedpage = feedRepo.findByCategoryAndIsDeletedOrderByCreatedDateDesc(category,false, pageable);

			}
		}

		if (feedpage != null && feedpage.hasContent()) {
			return convertFeedtoFeedDTO(feedpage.getContent());
		}

		return null;
	}

	private List<FeedDTO> getFeedforMobile(Category category, Set<Department> department, int index, int size) {
		Page<Feed> feedpage = null;
		Pageable pageable = new PageRequest(index, size);
		if (category != null) {
			if (department != null && !department.isEmpty()) {
//				feedpage = feedRepo.findByCategoryAndDepartmentInOrderByCreatedDateDesc(category, department, pageable);
				feedpage = feedRepo.findByCategoryAndDepartmentInAndIsDeletedOrderByCreatedDateDesc(category, department, false,pageable);
			} else {
//				feedpage = feedRepo.findByCategoryOrderByCreatedDateDesc(category, pageable);
				feedpage = feedRepo.findByCategoryAndIsDeletedOrderByCreatedDateDesc(category,false, pageable);

			}
		}

		if (feedpage != null && feedpage.hasContent()) {
			return convertFeedtoFeedDTO(feedpage.getContent());
		}

		return null;
	}

	public GetFeedResponseDTO getFeedforMobile (String userId, Set<String> department, String type){
		GetFeedResponseDTO response = new GetFeedResponseDTO();
		Set<Department> depart = getDepartmentFromPayload(department);
		long totalsize = getFeedSize(Category.valueOf(type.toUpperCase()), depart);
		logger.info("total size if : {}", totalsize);

		List<FeedDTO> feed = getFeedforMobile(Category.valueOf(type.toUpperCase()), depart, 0, (int)totalsize);

		logger.info("feed fetched from Db are :{}", Arrays.toString(feed.toArray()));

		if (feed.isEmpty()) {
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage("Feed is Empty");
			return response;
		}
		response.setSize(totalsize);
		response.setFeed(feed);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;
	}

	private long getFeedSize(Category category, Set<Department> department) {

		long count;

		if (category != null) {
			if (department != null && !department.isEmpty()) {
				count = feedRepo.countByCategoryAndDepartmentIn(category, department);
			} else {
				count = feedRepo.countByCategory(category);
			}
		} else {
			count = 0;
		}

		return count;

	}

	private Set<Department> getDepartmentFromPayload(Set<String> department) {

		if (department == null || department.isEmpty()) {
			return null;
		}

		Set<Department> departments = new HashSet<>();
		for (String dept : department) {
			if (dept == null || dept.isEmpty()) {
				continue;
			}
			departments.add(Department.valueOf(dept.toUpperCase()));
		}
		return departments;

	}

	private List<FeedDTO> convertFeedtoFeedDTO(List<Feed> content) {

		List<FeedDTO> feed = new ArrayList<FeedDTO>();

		for (Feed f : content) {
			FeedDTO fe = new FeedDTO();
			BeanUtils.copyProperties(f, fe);
			fe.setCategory(f.getCategory().getValue());
			fe.setDepartment(f.getDepartment().getValue());
			feed.add(fe);

		}
		return feed;

	}

	@Override
	public GetContactResponseDTO getContact(String userId, Set<String> department, int index, int size) {
		GetContactResponseDTO response = new GetContactResponseDTO();

		if (size == 0) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage("Size cannot be 0");
			return response;
		}
		Set<Department> depart = getDepartmentFromPayload(department);
		long totalsize = getContactsSize(depart);
		logger.info("total size is : {}", totalsize);

		List<Contacts> contact = getContact(depart, index, size);

		logger.info("feed contacts from Db are :{}", Arrays.toString(contact.toArray()));

		if (contact.isEmpty()) {
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage("Contact is Empty");
			return response;
		}
		response.setSize(totalsize);
		response.setContacts(convertContactstoContactdDTO(contact));
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;

	}

	private List<Contacts> getContact(Set<Department> department, int index, int size) {
		

		Page<Contacts> contactpage = null;
		Pageable pageable = new PageRequest(index, size);

		if (department != null && !department.isEmpty()) {
			contactpage = contactRepo.findByDepartmentInOrderByCreatedDateDesc(department, pageable);
		} else {
			contactpage = contactRepo.findAll(pageable);
		}
	

		if (contactpage != null && contactpage.hasContent()) {
			return contactpage.getContent();
		}

		return null;
	

	}

	private long getContactsSize(Set<Department> department) {

		if (department != null && !department.isEmpty()) {

			return contactRepo.countByDepartmentIn(department);

		} else {
			return contactRepo.count();
		}

	}

	private List<ContactDTO> convertContactstoContactdDTO(List<Contacts> content) {

		List<ContactDTO> contacts = new ArrayList<ContactDTO>();

		for (Contacts f : content) {
			ContactDTO fe = new ContactDTO();
			BeanUtils.copyProperties(f, fe);
			fe.setDepartment(f.getDepartment().getValue());
			contacts.add(fe);

		}
		return contacts;
	}

	@Override
	public GetCalenderResponse getCalender(String userId) {

		GetCalenderResponse response = new GetCalenderResponse();

		CalenderEvents event = calRepo.findAll().get(0);
		if (event == null || event.getCalender() == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage("Calender Not Initialized");
			return response;
		}

		if (event.getCalender().isEmpty()) {
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage("Not Values in Calender");
			return response;
		}

		response.setCalender(event.getCalender());
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;

	}

	@Override
	public ServiceResponse shareFeed(String userId, String feedId) throws FeignCallException {
		ServiceResponse response = new ServiceResponse();

		Feed feed = feedRepo.findOneByFeedId(feedId);

		if (feed == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage("Feed not Found");
			return response;
		} else {
			String mailString = "";
			String feedType = feed.getCategory().toString();

			switch (feedType) {
			case "NEWS":

				mailString = "News shared is by Author : " + feed.getAuthorId() + "\n News is : " + feed.getNews();

				break;
			case "DEADLINES":

				mailString = "Deadline is for : " + feed.getNews() + "\n with date :" + feed.getDeadlineDate();
				break;
			case "EVENTS":

				mailString = "Event Title share is :" + feed.getEventTagline() + "\n Dated : " + feed.getDeadlineDate();
				break;
			default:
				response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
				response.setMessage("Incorrect FeedType");
				return response;
			}
			SendVerificationMailRequest request = new SendVerificationMailRequest();
			request.setTo(userId);
			request.setToken(mailString);
			com.rmit.main.library.mailer.model.ServiceResponse mailerResponse = mailerClient.share(request);
			if (mailerResponse == null) {
				throw new FeignCallException("Call to mailer failed");
			} else if (mailerResponse.getCode() != ApiResponseUtils.SUCCESS_CODE) {
				throw new FeignCallException(mailerResponse.getMessage());
			}

			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
			logger.info("feed shared successfully");

		}

		return response;
	}

}
