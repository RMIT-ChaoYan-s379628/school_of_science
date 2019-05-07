package com.rmit.gateway.utils;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rmit.main.library.gateway.api.WelcomeMessageRequest;
import com.rmit.main.library.mailer.model.ForgotPasswordMailRequest;
import com.rmit.main.library.mailer.model.SendVerificationMailRequest;
import com.rmit.main.library.mailer.model.ServiceResponse;

@FeignClient(name = "rmit-mailer-service")
public interface MailerServiceClient {

    @RequestMapping(value = "/mailer/sendMail/verification", method = RequestMethod.POST)
    public ServiceResponse sendVerificationMail(@RequestBody SendVerificationMailRequest request);

    @RequestMapping(value = "/mailer/sendMail/forgotPassword", method = RequestMethod.POST)
    public ServiceResponse forgotPassword(@RequestBody ForgotPasswordMailRequest request);
    
    @RequestMapping(value = "/mailer/sendMail/welcomemessage", method = RequestMethod.POST)
    public ServiceResponse welcomeMessage(@RequestBody WelcomeMessageRequest request) ;

}

