

package com.rmit.mailer.service.service;

import javax.mail.SendFailedException;

import com.rmit.mailer.library.request.ForgotPasswordMailRequest;
import com.rmit.mailer.library.request.SendVerificationMailRequest;
import com.rmit.mailer.library.request.WelcomeMessageRequest;
import com.rmit.mailer.library.response.ServiceResponse;


public interface ISendMailService {

    ServiceResponse sendVerificationMail(SendVerificationMailRequest request) throws SendFailedException;

    ServiceResponse forgotPassword(ForgotPasswordMailRequest request) throws SendFailedException;
    
    ServiceResponse share(SendVerificationMailRequest request) throws SendFailedException;

	ServiceResponse welcomeMessage(WelcomeMessageRequest request);


}
