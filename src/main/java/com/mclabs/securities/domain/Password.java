package com.mclabs.securities.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Password {

    private String password;
    private String matchingPassword;

    private String email;

    private String username;
    private String token;

}
