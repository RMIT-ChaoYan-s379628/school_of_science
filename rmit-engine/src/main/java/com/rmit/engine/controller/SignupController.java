package com.rmit.engine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmit.engine.inf.ISignUp;
import com.rmit.main.library.api.ApiRequestUtils;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.api.ServiceResponse;

@RestController
public class SignupController {

	private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

	@Autowired
	private ISignUp signupService;


	@RequestMapping(value = "/engine/verifyUser", method = RequestMethod.GET)
	public ServiceResponse verifyUser(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestParam(value = ApiRequestUtils.USER_TYPE) String userType) {
		ServiceResponse response = new ServiceResponse();
		try {
			response = signupService.verifyUser(userId, userType);
		} catch (Exception e) {
			logger.error("Some error occurred while verifying user");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/engine/getDepartments", method = RequestMethod.GET)
	public ServiceResponse getDepartments(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId) {
		ServiceResponse response = new ServiceResponse();
		try {
			response = signupService.getDepartment();
		} catch (Exception e) {
			logger.error("Some error occurred while verifying user");
			logger.error("Exception: ", e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	

}
