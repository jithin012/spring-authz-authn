package com.mclabs.securities.util;

import com.mclabs.securities.domain.User;
import com.mclabs.securities.service.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class AccountListener  implements ApplicationListener<OnCreateAccountEvent> {

    private String serverUrl = "http://localhost:8080/";

//    @Autowired
//    private JavaMailSender mailSender;

    @Autowired
    AccountServiceImpl accountService;

    @Override
    public void onApplicationEvent(OnCreateAccountEvent event) {
        this.confirmCreateAccount(event);
    }
    private void confirmCreateAccount(OnCreateAccountEvent event) {
        // get the account
        // create verification token

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        accountService.createVerificationToken(user, token);

        // get email properties
        String recipientAddress = user.getUsername();
        String subject = "Account Confirmation";
        String confirmationUrl = event.getAppUrl() + "/accountConfirm?token=" + token;
        String message = "message body";
        log.info("confirm account url={}", serverUrl +confirmationUrl);
        // send email
//        SimpleMailMessage email = new SimpleMailMessage();
//        emai.setTo(recipientAddress);
//        email.setSubject(subject);
//        email.setText(message + "\r\n" + serverUrl +confirmationUrl);
//        mailSender.send(email);
    }
}
