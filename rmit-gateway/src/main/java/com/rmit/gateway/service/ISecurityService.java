package com.rmit.gateway.service;


import com.rmit.main.library.exception.FeignCallException;
import com.rmit.main.library.gateway.api.ChangePasswordRequest;
import com.rmit.main.library.gateway.api.ForgotPasswordRequest;
import com.rmit.main.library.gateway.api.ServiceResponse;
import com.rmit.main.library.gateway.api.SetPasswordRequest;
import com.rmit.main.library.gateway.api.VerifyTokenResponse;
import com.rmit.main.library.gateway.dto.UserDTO;

public interface ISecurityService {
	
	UserDTO getUserDetails();
	
	VerifyTokenResponse verifyToken(String mail, String token) throws FeignCallException;
	
	VerifyTokenResponse resetPassword(String mail, String token) throws FeignCallException;

    ServiceResponse setPassword(SetPasswordRequest request) throws FeignCallException;

    ServiceResponse forgotPassword(ForgotPasswordRequest request) throws Exception;

    ServiceResponse changePassword(ChangePasswordRequest request) throws Exception;

    
}
