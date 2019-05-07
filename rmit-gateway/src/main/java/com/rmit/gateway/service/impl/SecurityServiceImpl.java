package com.rmit.gateway.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rmit.gateway.service.ISecurityService;
import com.rmit.gateway.service.IUserService;
import com.rmit.gateway.utils.EngineClient;
import com.rmit.gateway.utils.MailerServiceClient;
import com.rmit.gateway.utils.SecurityUtils;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.exception.FeignCallException;
import com.rmit.main.library.gateway.api.ChangePasswordRequest;
import com.rmit.main.library.gateway.api.ForgotPasswordRequest;
import com.rmit.main.library.gateway.api.ServiceResponse;
import com.rmit.main.library.gateway.api.SetPasswordRequest;
import com.rmit.main.library.gateway.api.VerifyTokenResponse;
import com.rmit.main.library.gateway.api.WelcomeMessageRequest;
import com.rmit.main.library.gateway.dto.UserDTO;
import com.rmit.main.library.gateway.enums.UserRole;
import com.rmit.main.library.gateway.enums.UserStatus;
import com.rmit.main.library.gateway.model.User;
import com.rmit.main.library.gateway.repository.UserRepository;
import com.rmit.main.library.mailer.model.ForgotPasswordMailRequest;

@Service
public class SecurityServiceImpl implements ISecurityService {

	private static final String INVALID_USER_ERROR_MESSAGE = "User does not exist";

	private static final String INVALID_TOKEN_ERROR_MESSAGE = "Invalid or expired token";

	private static final String MAILER_CALL_ERROR_MESSAGE = "Call to mailer failed";

	private static final String ENGINE_CALL_ERROR_MESSAGE = "Call to Engine failed";

	@Value("${security.user.name}")
	private String username;

	@Autowired
	private IUserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailerServiceClient mailerClient;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EngineClient engineClient;

	@Override
	public UserDTO getUserDetails() {
		String userId = SecurityUtils.getCurrentLogin();
		if (username.equals(userId)) {
			return new UserDTO(username, UserRole.ADMIN,"ALL");
		}
		User user = userService.findUserByUserId(userId);
		UserDTO userDTO = null;
		if (user != null) {
			userDTO = new UserDTO(user.getUserId(), user.getRole(),user.getDepartment());
		}
		return userDTO;
	}

	@Override
	public VerifyTokenResponse verifyToken(String mail, String token) throws FeignCallException {
		VerifyTokenResponse response = new VerifyTokenResponse();
		User user = userRepository.findOneByUserIdAndStatus(mail, UserStatus.UNVERIFIED);
		if (user == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_USER_ERROR_MESSAGE);
			return response;
		}
		if (!token.equals(user.getToken())) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_TOKEN_ERROR_MESSAGE);
			return response;
		}
		com.rmit.main.library.api.ServiceResponse resp = engineClient.verifyUser(user.getUserId(), user.getRole().toString());
		if (resp == null) {
			throw new FeignCallException(ENGINE_CALL_ERROR_MESSAGE);
		} else if (resp.getCode() != ApiResponseUtils.SUCCESS_CODE) {
			throw new FeignCallException(resp.getMessage());
		}
		user.setStatus(UserStatus.ACTIVE);
		userRepository.save(user);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		response.setVerified(true);
		return response;
	}
	
	@Override
	public VerifyTokenResponse resetPassword(String mail, String token) throws FeignCallException {
		VerifyTokenResponse response = new VerifyTokenResponse();
		User user = userRepository.findOneByUserIdAndStatus(mail, UserStatus.ACTIVE);
		if (user == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_USER_ERROR_MESSAGE);
			return response;
		}
		if (!token.equals(user.getToken())) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_TOKEN_ERROR_MESSAGE);
			return response;
		}
		
		user.setStatus(UserStatus.ACTIVE);
		userRepository.save(user);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;
	}
	

	@Override
	public ServiceResponse setPassword(SetPasswordRequest request) throws FeignCallException {
		ServiceResponse response = new ServiceResponse();
		User user = userRepository.findOneByUserId(request.getMail());
		if (user == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_USER_ERROR_MESSAGE);
			return response;
		}
		if (!user.getToken().equals(request.getToken())) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_TOKEN_ERROR_MESSAGE);
			return response;
		}
		user.setStatus(UserStatus.ACTIVE);
		user.setToken(null);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		WelcomeMessageRequest mailerRequest = new WelcomeMessageRequest();
		mailerRequest.setTo(request.getMail());
		
		com.rmit.main.library.mailer.model.ServiceResponse mailerResponse = mailerClient.welcomeMessage(mailerRequest);
		if(mailerResponse == null) {
			throw new FeignCallException(MAILER_CALL_ERROR_MESSAGE);
		}else if(mailerResponse.getCode() != ApiResponseUtils.SUCCESS_CODE) {
			throw new FeignCallException(mailerResponse.getMessage());
		}
		
		userRepository.save(user);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;
	}

	@Override
	public ServiceResponse forgotPassword(ForgotPasswordRequest request) throws Exception {
		ServiceResponse response = new ServiceResponse();
		User user = userRepository.findOneByUserIdAndStatus(request.getMail(), UserStatus.ACTIVE);
		if (user == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_USER_ERROR_MESSAGE);
			return response;
		}
		String token = UUID.randomUUID().toString();
		user.setToken(token);
		// call mailer
		ForgotPasswordMailRequest mailerRequest = new ForgotPasswordMailRequest();
		mailerRequest.setTo(request.getMail());
		mailerRequest.setToken(token);
		com.rmit.main.library.mailer.model.ServiceResponse mailerResponse = mailerClient.forgotPassword(mailerRequest);
		if (mailerResponse == null) {
			throw new FeignCallException(MAILER_CALL_ERROR_MESSAGE);
		} else if (mailerResponse.getCode() != ApiResponseUtils.SUCCESS_CODE) {
			throw new FeignCallException(mailerResponse.getMessage());
		}
		userRepository.save(user);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;
	}

	@Override
	public ServiceResponse changePassword(ChangePasswordRequest request) throws Exception {
		ServiceResponse response = new ServiceResponse();
		String userId = SecurityUtils.getCurrentLogin();
		if (userId == null || userId.isEmpty()) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_USER_ERROR_MESSAGE);
			return response;
		}
		User user = userRepository.findOneByUserIdAndStatus(userId, UserStatus.ACTIVE);
		if (user == null) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_USER_ERROR_MESSAGE);
			return response;
		}
		if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage("Old password is incorrect");
			return response;
		}
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;
	}

}
