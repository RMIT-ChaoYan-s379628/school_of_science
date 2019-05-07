
package com.rmit.mailer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.rmit")
public class MailerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailerServiceApplication.class, args);
    }

}
