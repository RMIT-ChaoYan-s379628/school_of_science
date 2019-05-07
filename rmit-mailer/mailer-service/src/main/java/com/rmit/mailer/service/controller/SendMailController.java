
package com.rmit.mailer.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmit.mailer.library.request.ForgotPasswordMailRequest;
import com.rmit.mailer.library.request.SendVerificationMailRequest;
import com.rmit.mailer.library.request.WelcomeMessageRequest;
import com.rmit.mailer.library.response.ServiceResponse;
import com.rmit.mailer.service.service.ISendMailService;


@RestController
@RequestMapping("/mailer/sendMail")
public class SendMailController {

    private static final Logger LOG = LoggerFactory.getLogger(SendMailController.class);

    @Autowired
    private ISendMailService    sendMailService;

    @RequestMapping(value = "/verification", method = RequestMethod.POST)
    public ServiceResponse sendVerificationMail(@RequestBody SendVerificationMailRequest request) {
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(1);
            response.setMessage("Invalid request");
            return response;
        }
        try {
            response = sendMailService.sendVerificationMail(request);
        } catch (Exception e) {
            LOG.error("Some error while sending verification mail to " + request.getTo());
            LOG.error("Exception: ", e);
            response.setCode(2);
            response.setMessage(e.getMessage());
            return response;
        }
        return response;
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ServiceResponse forgotPassword(@RequestBody ForgotPasswordMailRequest request) {
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(1);
            response.setMessage("Invalid request");
            return response;
        }
        try {
            response = sendMailService.forgotPassword(request);
        } catch (Exception e) {
            LOG.error("Some error occured while sending forgot password mail to " + request.getTo());
            LOG.error("Exception: ", e);
            response.setCode(2);
            response.setMessage(e.getMessage());
            return response;
        }
        return response;
    }
    
    @RequestMapping(value = "/share", method = RequestMethod.POST)
    public ServiceResponse share(@RequestBody SendVerificationMailRequest request) {
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(1);
            response.setMessage("Invalid request");
            return response;
        }
        try {
            response = sendMailService.share(request);
        } catch (Exception e) {
            LOG.error("Some error occured while sharing feed " + request.getTo());
            LOG.error("Exception: ", e);
            response.setCode(2);
            response.setMessage(e.getMessage());
            return response;
        }
        return response;
    }
    
    @RequestMapping(value = "/welcomemessage", method = RequestMethod.POST)
    public ServiceResponse welcomeMessage(@RequestBody WelcomeMessageRequest request) {
        ServiceResponse response = new ServiceResponse();
        if (request == null) {
            response.setCode(1);
            response.setMessage("Invalid request");
            return response;
        }
        try {
            response = sendMailService.welcomeMessage(request);
        } catch (Exception e) {
            LOG.error("Some error occured sending welcome message " + request.getTo());
            LOG.error("Exception: ", e);
            response.setCode(2);
            response.setMessage(e.getMessage());
            return response;
        }
        return response;
    }
    
    

}
