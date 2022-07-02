package com.mclabs.securities.repo;

import com.mclabs.securities.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);
    void deleteByToken(String token);
}
