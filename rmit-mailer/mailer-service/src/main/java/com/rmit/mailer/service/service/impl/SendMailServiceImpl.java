
package com.rmit.mailer.service.service.impl;

import javax.mail.SendFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rmit.mailer.library.request.ForgotPasswordMailRequest;
import com.rmit.mailer.library.request.SendVerificationMailRequest;
import com.rmit.mailer.library.request.WelcomeMessageRequest;
import com.rmit.mailer.library.response.ServiceResponse;
import com.rmit.mailer.service.service.ISendMailService;


@Service
public class SendMailServiceImpl implements ISendMailService {

    private static final Logger LOG                    = LoggerFactory.getLogger(SendMailServiceImpl.class);
    
    private static final String verificationMailText   = "Please click on this link to verify your email address and set your password for RMIT. Thanks. HOST_ADDRESSauth/setpassword?mail=TO_EMAIL&token=TOKEN";
    private static final String forgotPasswordMailText = "Please click on this link to reset your password for RMIT. Thanks. HOST_ADDRESSauth/resetpassword?mail=TO_EMAIL&token=TOKEN";
    
    private static final String welcomeText = "Please Download RMIT Mobile Applications. Links are as belows : \n Android : demolink \n Ios: demolink";
    
    private static final String feedshareText = "Here is the Copy of the RMIT Mobile App Feed \n ";

    @Value("${host.address}")
    private String              HOST_ADDRESS;

    @Value("${mail.ccAddresses}")
    private String              ccAddresses;

    @Autowired
    private JavaMailSender      mailSender;

    public ServiceResponse sendVerificationMail(SendVerificationMailRequest request) throws SendFailedException {
        ServiceResponse response = new ServiceResponse();
        LOG.info("Sending verification mail to " + request.getTo());
        SimpleMailMessage message = new SimpleMailMessage();
        String mailText = verificationMailText.replaceAll("TO_EMAIL", request.getTo());
        mailText = mailText.replaceAll("TOKEN", request.getToken());
        mailText = mailText.replaceAll("HOST_ADDRESS", HOST_ADDRESS);
        message.setTo(request.getTo());
        message.setCc(ccAddresses.split(","));
        message.setSubject("RMIT Verification Mail");
        message.setText(mailText);
        mailSender.send(message);
        response.setCode(0);
        response.setMessage("SUCCESS");
        return response;
    }

    public ServiceResponse forgotPassword(ForgotPasswordMailRequest request) throws SendFailedException {
        ServiceResponse response = new ServiceResponse();
        LOG.info("Sending forgot password mail to " + request.getTo());
        SimpleMailMessage message = new SimpleMailMessage();
        String mailText = forgotPasswordMailText.replaceAll("TO_EMAIL", request.getTo());
        mailText = mailText.replaceAll("TOKEN", request.getToken());
        mailText = mailText.replaceAll("HOST_ADDRESS", HOST_ADDRESS);
        message.setTo(request.getTo());
        message.setCc(ccAddresses.split(","));
        message.setSubject("RMIT Reset Password");
        message.setText(mailText);
        mailSender.send(message);
        response.setCode(0);
        response.setMessage("SUCCESS");
        return response;

    }

	public ServiceResponse share(SendVerificationMailRequest request) throws SendFailedException {
		ServiceResponse response = new ServiceResponse();
        LOG.info("Sharing Feed to  " + request.getTo());
        SimpleMailMessage message = new SimpleMailMessage();
        String mailText = feedshareText+request.getToken();
        message.setTo(request.getTo());
        message.setCc(ccAddresses.split(","));
        message.setSubject("RMIT Feed Shared");
        message.setText(mailText);
        mailSender.send(message);
        response.setCode(0);
        response.setMessage("SUCCESS");
        return response;
	}

	public ServiceResponse welcomeMessage(WelcomeMessageRequest request) {
		
		ServiceResponse response = new ServiceResponse();
        LOG.info("Sending Welcome Message to   " + request.getTo());
        SimpleMailMessage message = new SimpleMailMessage();
        String mailText = welcomeText;
        message.setTo(request.getTo());
        message.setCc(ccAddresses.split(","));
        message.setSubject("Welcome To RMIT");
        message.setText(mailText);
        mailSender.send(message);
        response.setCode(0);
        response.setMessage("SUCCESS");
        return response;
		
	}

}
