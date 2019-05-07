
package com.rmit.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmit.gateway.service.ISecurityService;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.gateway.api.ChangePasswordRequest;
import com.rmit.main.library.gateway.api.ForgotPasswordRequest;
import com.rmit.main.library.gateway.api.ServiceResponse;
import com.rmit.main.library.gateway.api.SetPasswordRequest;
import com.rmit.main.library.gateway.api.VerifyTokenResponse;
import com.rmit.main.library.gateway.dto.UserDTO;

@Controller
public class SecurityContoller {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityContoller.class);

    @Autowired
    private ISecurityService    securityService;

    @RequestMapping(value = "/security/account", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO getUserDTO() {
        return securityService.getUserDetails();
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ResponseBody
    public VerifyTokenResponse verifyToken(@RequestParam(value = "mail", required = true) String mail,
    		@RequestParam(value = "token", required = true) String token) {
    	LOG.info("/verify, mail {}, token {}", mail, token);
        VerifyTokenResponse response = new VerifyTokenResponse();
        try {
            response = securityService.verifyToken(mail, token);
        } catch (Exception e) {
            LOG.error("Some error occured while verifiying token");
            LOG.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/setPassword", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse setPassword(@RequestBody SetPasswordRequest request) {
    	LOG.info("/setPassword, request {}", request);
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = securityService.setPassword(request);
        } catch (Exception e) {
            LOG.error("Some error occured while setting password");
            LOG.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse forgotPassword(@RequestBody ForgotPasswordRequest request) {
    	LOG.info("/forgotPassword, request {}", request);
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = securityService.forgotPassword(request);
        } catch (Exception e) {
            LOG.error("Some error occured while calling forgot password");
            LOG.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse changePassword(@RequestBody ChangePasswordRequest request) {
    	LOG.info("/changePassword, request {}", request);
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = securityService.changePassword(request);
        } catch (Exception e) {
            LOG.error("Some error occured while changing password");
            LOG.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    @ResponseBody
    public VerifyTokenResponse resetPassword(@RequestParam(value = "mail", required = true) String mail,
    		@RequestParam(value = "token", required = true) String token) {
    	LOG.info("/resetPassword, mail {}, token {}", mail, token);
        VerifyTokenResponse response = new VerifyTokenResponse();
        try {
            response = securityService.resetPassword(mail, token);
        } catch (Exception e) {
            LOG.error("Some error occured while verifiying token");
            LOG.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    
}
