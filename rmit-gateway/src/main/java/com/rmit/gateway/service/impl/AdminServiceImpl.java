package com.rmit.gateway.service.impl;

import java.util.UUID;

import com.rmit.gateway.controller.request.DeleteAdminRequest;
import com.rmit.main.library.api.GetUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmit.gateway.service.IAdminService;
import com.rmit.gateway.utils.EngineClient;
import com.rmit.gateway.utils.MailerServiceClient;
import com.rmit.gateway.utils.SecurityUtils;
import com.rmit.main.library.api.AddClientRequest;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.exception.FeignCallException;
import com.rmit.main.library.gateway.api.ServiceResponse;
import com.rmit.main.library.gateway.enums.UserRole;
import com.rmit.main.library.gateway.enums.UserStatus;
import com.rmit.main.library.gateway.model.User;
import com.rmit.main.library.gateway.repository.UserRepository;
import com.rmit.main.library.mailer.model.SendVerificationMailRequest;

@Service
public class AdminServiceImpl implements IAdminService {

    Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private static final String INVALID_USER_ERROR_MESSAGE = "User does not exist";

    private static final String MAILER_CALL_ERROR_MESSAGE = "Call to mailer failed";

    private static final String ENGINE_ERROR_MESSAGE = "Call to Engine Failed";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailerServiceClient mailerClient;

    @Autowired
    private EngineClient engineClient;

    @Override
    public ServiceResponse addClient(AddClientRequest request) throws Exception {
        logger.info("added client ");
        ServiceResponse response = new ServiceResponse();
        String userId = SecurityUtils.getCurrentLogin();

        logger.info("user id siddharth" + userId);
        if (userId == null || userId.isEmpty()) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(INVALID_USER_ERROR_MESSAGE);
            return response;
        }
        User client = userRepository.findOneByUserId(request.getUserId());
        if (client != null) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage("Client already exists");
            return response;
        }

        logger.info("client not present so moving ahead");
        client = new User();
        client.setUserId(request.getUserId());
        client.setRole(UserRole.CLIENT);
        client.setStatus(UserStatus.UNVERIFIED);
        client.setDepartment(request.getDepartment());
        String token = UUID.randomUUID().toString();
        client.setToken(token);
        logger.info("created new user BO and now sending mail");
        // call mailer
        SendVerificationMailRequest mailerRequest = new SendVerificationMailRequest();
        mailerRequest.setTo(request.getUserId());
        mailerRequest.setToken(token);
        com.rmit.main.library.mailer.model.ServiceResponse mailerResponse = mailerClient
                .sendVerificationMail(mailerRequest);
        logger.info("mailer service working");
        if (mailerResponse == null) {
            throw new FeignCallException(MAILER_CALL_ERROR_MESSAGE);
        } else if (mailerResponse.getCode() != ApiResponseUtils.SUCCESS_CODE) {
            throw new FeignCallException(mailerResponse.getMessage());
        }

        com.rmit.main.library.api.ServiceResponse engineResponse = engineClient.addFaculty(userId, request);
        if (engineResponse == null) {
            throw new FeignCallException(ENGINE_ERROR_MESSAGE);
        } else if (engineResponse.getCode() != ApiResponseUtils.SUCCESS_CODE) {
            throw new FeignCallException(ENGINE_ERROR_MESSAGE);
        }
        userRepository.save(client);
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
        return response;
    }

    @Override
    public ServiceResponse deleteUser(DeleteAdminRequest request) {

        ServiceResponse response = new ServiceResponse();

        User client = userRepository.findOneByUserId(request.getUserId());
        if (client == null) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage("Client is not exists");
            return response;
        }

        userRepository.delete(client);
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage("admin is deleted");
        return response;
    }

}
