package com.rmit.gateway.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.rmit.gateway.controller.response.GetClientsResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmit.gateway.service.IClientService;
import com.rmit.gateway.utils.EngineClient;
import com.rmit.gateway.utils.MailerServiceClient;
import com.rmit.gateway.utils.SecurityUtils;
import com.rmit.main.library.api.AddUserDTO;
import com.rmit.main.library.api.AddUsersRequest;
import com.rmit.main.library.api.AddUsersResponse;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.api.DisableUsersRequest;
import com.rmit.main.library.exception.FeignCallException;
import com.rmit.main.library.gateway.api.ServiceResponse;
import com.rmit.main.library.gateway.enums.UserRole;
import com.rmit.main.library.gateway.enums.UserStatus;
import com.rmit.main.library.gateway.model.User;
import com.rmit.main.library.gateway.repository.UserRepository;
import com.rmit.main.library.mailer.model.SendVerificationMailRequest;

@Service
public class ClientServiceImpl implements IClientService {

	private static final String INVALID_USER_ERROR_MESSAGE = "User does not exist";

	private static final String MAILER_CALL_ERROR_MESSAGE = "Call to mailer failed";

	private static final String ENGINE_CALL_ERROR_MESSAGE = "Call to egine failed";
	
	Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailerServiceClient mailerClient;

	@Autowired
	EngineClient engineClient;

	@Override
	public AddUsersResponse addUsers(AddUsersRequest request) throws Exception {
		AddUsersResponse response = new AddUsersResponse();
		String userId = SecurityUtils.getCurrentLogin();
		if (userId == null || userId.isEmpty()) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_USER_ERROR_MESSAGE);
			return response;
		}
		Map<String, Boolean> inviteStatus = new HashMap<>();
		List<User> validUsers = new ArrayList<>();
		List<AddUserDTO> validUserDTOs = new ArrayList<>();
		for (AddUserDTO userDTO : request.getUsers()) {
			User user = userRepository.findOneByUserId(userDTO.getUserId());
			if (user != null) {
				inviteStatus.put(userDTO.getUserId(), false);
			} else {
				inviteStatus.put(userDTO.getUserId(), true);
				user = new User();
				user.setUserId(userDTO.getUserId());
				user.setRole(UserRole.USER);
				user.setDepartment(userDTO.getDepartment());
				user.setStatus(UserStatus.UNVERIFIED);
				String token = UUID.randomUUID().toString();
				user.setToken(token);
				// call mailer
				SendVerificationMailRequest mailerRequest = new SendVerificationMailRequest();
				mailerRequest.setTo(userDTO.getUserId());
				mailerRequest.setToken(token);
				com.rmit.main.library.mailer.model.ServiceResponse mailerResponse = mailerClient
						.sendVerificationMail(mailerRequest);
				if (mailerResponse == null) {
					throw new FeignCallException(MAILER_CALL_ERROR_MESSAGE);
				} else if (mailerResponse.getCode() != ApiResponseUtils.SUCCESS_CODE) {
					throw new FeignCallException(mailerResponse.getMessage());
				}
				validUsers.add(user);
				AddUserDTO engineUserDTO = new AddUserDTO();
				BeanUtils.copyProperties(userDTO, engineUserDTO);
				validUserDTOs.add(engineUserDTO);
			}
		}
		request.setUsers(validUserDTOs);
		com.rmit.main.library.api.ServiceResponse engineResponse = engineClient.addUsers(userId, request);
		if (engineResponse == null) {
			throw new FeignCallException(ENGINE_CALL_ERROR_MESSAGE);
		} else if (engineResponse.getCode() != ApiResponseUtils.SUCCESS_CODE) {
			throw new FeignCallException(engineResponse.getMessage());
		}

		userRepository.save(validUsers);
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		response.setInviteStatus(inviteStatus);
		return response;
	}

	@Override
	public ServiceResponse disableUsers(DisableUsersRequest request) throws Exception {
		ServiceResponse response = new ServiceResponse();
		String userId = SecurityUtils.getCurrentLogin();
		if (userId == null || userId.isEmpty()) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(INVALID_USER_ERROR_MESSAGE);
			return response;
		}
		if (request != null && !request.getUserIds().isEmpty()) {
			List<String> userIdsClient = new ArrayList();
			List<String> userIdsUser = new ArrayList();
			for (String disabledUserId : request.getUserIds()) {
				User user = userRepository.findOneByUserIdAndStatus(disabledUserId, UserStatus.ACTIVE);
				logger.info("user {}" ,user);
				if (user != null) {
					user.setStatus(UserStatus.DISABLED);
					if (user.getRole().equals(UserRole.CLIENT)) {
						logger.info("added in client");
						userIdsClient.add(disabledUserId);
						
					} else if (user.getRole().equals(UserRole.USER)) {
						logger.info("added in user");
						userIdsUser.add(disabledUserId);

					}
					userRepository.save(user);
				}
			}
			
			logger.info("Calling Client with user Ids {}",userIdsClient);
			logger.info("Disable users {}",userIdsUser);
			
			if(!userIdsClient.isEmpty()) {
				DisableUsersRequest d = new DisableUsersRequest();
				d.setUserIds(userIdsClient);
				com.rmit.main.library.api.ServiceResponse engineResponse = engineClient.disableUsers(userId, d);
				logger.info("engine Response {}",engineResponse);
				if(engineResponse == null) {
					throw new FeignCallException(ENGINE_CALL_ERROR_MESSAGE);
				}else if (engineResponse.getCode()!= ApiResponseUtils.SUCCESS_CODE){
					throw new FeignCallException(engineResponse.getMessage());
				}
			}
			if(!userIdsUser.isEmpty()) {
				DisableUsersRequest d = new DisableUsersRequest();
				d.setUserIds(userIdsUser);
				com.rmit.main.library.api.ServiceResponse engineResponse = engineClient.disableClientUsers(userId, d);
				logger.info("engine Response {}",engineResponse);
				if(engineResponse == null) {
					throw new FeignCallException(ENGINE_CALL_ERROR_MESSAGE);
				}else if (engineResponse.getCode()!= ApiResponseUtils.SUCCESS_CODE){
					throw new FeignCallException(engineResponse.getMessage());
				}
			}
		}
		response.setCode(ApiResponseUtils.SUCCESS_CODE);
		response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
		return response;
	}

	@Override
	public List<User> getClients(int index,int size){
		final Pageable pageRequest=new PageRequest(index,size);
		Page<User> page=userRepository.findAll(pageRequest);
		return page.getContent() ;
	}

}
