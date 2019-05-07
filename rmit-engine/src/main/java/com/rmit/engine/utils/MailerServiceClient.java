package com.rmit.engine.utils;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rmit.main.library.mailer.model.SendVerificationMailRequest;
import com.rmit.main.library.mailer.model.ServiceResponse;

@FeignClient(name = "rmit-mailer-service")
public interface MailerServiceClient {

    @RequestMapping(value = "/mailer/sendMail/share", method = RequestMethod.POST)
    public ServiceResponse share(@RequestBody SendVerificationMailRequest request); 

}

