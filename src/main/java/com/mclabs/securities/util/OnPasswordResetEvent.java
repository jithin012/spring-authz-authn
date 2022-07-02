package com.mclabs.securities.util;

import com.mclabs.securities.domain.Password;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnPasswordResetEvent extends ApplicationEvent {
    private String appUrl;
    private Password password;

    public OnPasswordResetEvent(Password password, String appUrl) {
        super(password);
        this.password = password;
        this.appUrl = appUrl;
    }
}
