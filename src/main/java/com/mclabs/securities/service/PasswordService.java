package com.mclabs.securities.service;

import com.mclabs.securities.domain.Password;
import com.mclabs.securities.domain.ResetToken;

public interface PasswordService {
    void  createResetToken( Password password, String token);

    boolean confirmResetToken(ResetToken token);

    void update(Password password, String username);
}
