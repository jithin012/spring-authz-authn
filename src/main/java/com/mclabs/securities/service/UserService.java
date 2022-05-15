package com.mclabs.securities.service;

import com.mclabs.securities.domain.Role;
import com.mclabs.securities.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
