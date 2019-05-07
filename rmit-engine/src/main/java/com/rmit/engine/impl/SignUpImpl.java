package com.rmit.engine.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmit.engine.inf.ISignUp;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.api.GetDepartmentResponse;
import com.rmit.main.library.api.ServiceResponse;
import com.rmit.main.library.enums.Department;
import com.rmit.main.library.enums.UserStatus;
import com.rmit.main.library.feed.model.Faculty;
import com.rmit.main.library.feed.model.User;
import com.rmit.main.library.repository.AUserRepository;
import com.rmit.main.library.repository.FacultyRepository;


@Service
public class SignUpImpl implements ISignUp {

	public static final String CLIENT = "client";

	public static final String USER = "user";

	@Autowired
	private FacultyRepository facultyRepo;

	@Autowired
	private AUserRepository userRepo;


	public boolean verifyFaculty(String userId) {

		Faculty faculty = facultyRepo.findOneByUserIdAndStatus(userId, UserStatus.UNVERIFIED);
		if (faculty == null) {
			return false;
		}
		faculty.setStatus(UserStatus.ACTIVE);
		faculty.setUpdatedDate(new Date());
		facultyRepo.save(faculty);
		return true;
	}

	public boolean verifyUser(String userId) {
		User user = userRepo.findOneByUserIdAndStatus(userId, UserStatus.UNVERIFIED);
		if (user == null) {
			return false;
		}
		user.setStatus(UserStatus.ACTIVE);
		user.setUpdatedDate(new Date());
		userRepo.save(user);
		return true;

	}

	@Override
	public ServiceResponse verifyUser(String userId, String userType) {

		ServiceResponse response = new ServiceResponse();

		boolean success = true;
		switch (userType.toLowerCase()) {
		case CLIENT:
			success = verifyFaculty(userId);
			break;
		case USER:
			success = verifyUser(userId);
			break;
		default:
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		if (!success) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage("User status is not unverified");
			return response;
		}
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;

	}

	@Override
	public ServiceResponse getDepartment() {
		GetDepartmentResponse response = new GetDepartmentResponse();
		
		List<String> dep = new ArrayList<>();
		
	   for(Department d: Department.values()) {
		   dep.add(d.toString());
	   }
	   dep.remove("ALL");
	   response.setCode(ApiResponseUtils.SUCCESS_CODE);
	   response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
	   response.setDepartments(dep);
		return response;
	}


}