package com.mclabs.securities.util;

import com.mclabs.securities.domain.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnCreateAccountEvent  extends ApplicationEvent {
    private String appUrl;
    private User user;

    public OnCreateAccountEvent(User user, String appUrl) {
        super(user);

        this.user = user;
        this.appUrl = appUrl;
    }
}
