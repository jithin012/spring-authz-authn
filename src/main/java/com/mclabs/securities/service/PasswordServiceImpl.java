package com.mclabs.securities.service;

import com.mclabs.securities.domain.Password;
import com.mclabs.securities.domain.ResetToken;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Override
    public void createResetToken(Password password, String token) {
        // make similar to accountServiceImpl.createVerificationToken
    }

    @Override
    public boolean confirmResetToken(ResetToken token) {
        return false;
    }

    @Override
    public void update(Password password, String username) {
        // passwordRepo.update(password. username);
        // update password by username
    }
}
