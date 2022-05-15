package com.mclabs.securities.repo;

import com.mclabs.securities.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
