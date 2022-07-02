package com.mclabs.securities.service;

import com.mclabs.securities.domain.Role;
import com.mclabs.securities.domain.User;
import com.mclabs.securities.repo.RoleRepo;
import com.mclabs.securities.repo.UserRepo;
import com.mclabs.securities.util.OnCreateAccountEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("user not found in the db");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("user found in the db {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
    @Override
    public User saveUser(User user) {
        // 1. check for error
        // 2. verify  account and user doesn't exist
        // 3. verify email address
        // 4. encrypt password
        // 5. create account
        // 6. fire off an event on creation

        log.info("saving new user {} to database", user.getName());
        // step 4.5
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser =  userRepo.save(user);

        //step 6. fire off an event
        eventPublisher.publishEvent(new OnCreateAccountEvent(newUser, "email_app"));

        return newUser;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role {} to the user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("get user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all user");
        return userRepo.findAll();
    }
}
