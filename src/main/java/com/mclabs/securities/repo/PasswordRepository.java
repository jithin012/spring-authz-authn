package com.mclabs.securities.repo;

import com.mclabs.securities.domain.Password;
import com.mclabs.securities.domain.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<ResetToken, String> {
    //void saveToken(ResetToken resetToken);

    ResetToken findByToken(String token);

    //void update(Password password, String username);
}
