package com.mclabs.securities.controller;

import com.mclabs.securities.domain.Password;
import com.mclabs.securities.service.PasswordService;
import com.mclabs.securities.util.OnPasswordResetEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PasswordController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/password")
    public String sendEmailToReset(@RequestBody Password password) {
        // check for errors
        // should verify valid email address
        // verify email from database
        // fire off an event to reset email

        eventPublisher.publishEvent(new OnPasswordResetEvent(password, "email_app"));
        return "email sent to your email. Please check";
    }

    @GetMapping("/passwordReset")
    public String getNewPassword(@RequestParam("token") String token) {
        // verify token
        Password password = new Password();
        password.setToken(token);
        // return a model page for enter new password and confirm password
        // then from that page hit for post api "/passwordReset" hence update password
        return "new ModelandView('resetPassoword', 'password', password)";
    }

    @PostMapping("/passwordReset")
    public String saveNewPassword(@RequestParam("token") String token,
                                  @RequestBody Password password) {
        // should match the password
        // verify token
        /**

         ResetToken resetToken = passwordRepository.findByToken(token);
         if (resetToken.getExpiryDate().after(new Date())) {
         password.setPassword(encoder.encode(password.getPassword()));
         passwordService.update(password, resetToken.getUsername());
         return "redirect:passwordReset?reset=true&token=0";
         } else {
         return "tokenExpired";
         }

         */
        return "password reset successfullu!!!";

    }
}
