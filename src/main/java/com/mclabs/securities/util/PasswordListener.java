package com.mclabs.securities.util;

import com.mclabs.securities.domain.Password;
import com.mclabs.securities.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class PasswordListener implements ApplicationListener<OnPasswordResetEvent> {
    private String serverUrl = "http://localhost:8080/";

    @Autowired
    private PasswordService passwordService;
    // private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnPasswordResetEvent event) {
        this.resetPassword(event);
    }

    public void resetPassword(OnPasswordResetEvent event) {
        // create password token
        // get email properties
        // send email
        Password password = event.getPassword();
        String token = UUID.randomUUID().toString();
        passwordService.createResetToken(password, token);

        String recipientAddress = password.getEmail();
        String subject = "Reset Password";
        String confirmationUrl = event.getAppUrl() + "/passwordReset?token=" + token;
        String message = "Reset your password. message body";
        log.info("confirm account url={}", serverUrl +confirmationUrl);
        // send email
//        SimpleMailMessage email = new SimpleMailMessage();
//        emai.setTo(recipientAddress);
//        email.setSubject(subject);
//        email.setText(message + "\r\n" + serverUrl +confirmationUrl);
//        mailSender.send(email);
    }
}
