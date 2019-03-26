package com.ag04.clidemo.config;

import com.ag04.clidemo.observer.UsersUpdateObserver;
import com.ag04.clidemo.service.MockUserService;
import com.ag04.clidemo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {

    @Bean
    public UserService userService(UsersUpdateObserver observer) {
        MockUserService userService = new MockUserService();
        userService.setObserver(observer);
        return userService;
    }

}
