
package com.rmit.mailer.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rmit.mailer.library.request.ForgotPasswordMailRequest;
import com.rmit.mailer.library.request.SendVerificationMailRequest;
import com.rmit.mailer.library.response.ServiceResponse;

@FeignClient(name = "rmit-mailer-service")
public interface MailerServiceClient {

    @RequestMapping(value = "/mailer/sendMail/verification", method = RequestMethod.POST)
    public ServiceResponse sendVerificationMail(@RequestBody SendVerificationMailRequest request);

    @RequestMapping(value = "/mailer/sendMail/forgotPassword", method = RequestMethod.POST)
    public ServiceResponse forgotPassword(@RequestBody ForgotPasswordMailRequest request);

}
