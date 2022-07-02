package com.mclabs.securities.service;

import com.mclabs.securities.domain.User;
import com.mclabs.securities.domain.VerificationToken;
import com.mclabs.securities.repo.AccountRepo;
import com.mclabs.securities.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AccountServiceImpl {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    UserRepo userRepo;

    private int counter = 0;
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        // TODO: make proper username
        String username = user.getUsername();
        if(!user.getUsername().contains("@")) {
            counter++;
            username = "jithin.m.baby+"+counter+"@gmail.com";
        }
        verificationToken.setUsername(username);
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(VerificationToken.EXPIRATION));
        accountRepo.save(verificationToken);
    }

    public void confirmAccount(String token) {
        //retrieve token
        // verify date
        // move from account table to user details @TODO
        // create user details @TODO
        // delete from accounts @TODO
        // delete from token

        VerificationToken verificationToken = accountRepo.findByToken(token);
        if(verificationToken.getExpiryDate().after(new Date())) {
            //@TODO
//           User user =  userRepo.findByUsername(verificationToken.getUsername());
            User user =  userRepo.findByUsername("test1234");
            accountRepo.deleteById("jithin.m.baby+5@gmail.com");
        }
    }
}
