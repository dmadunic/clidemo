package com.ag04.clidemo.config;

import com.ag04.clidemo.observer.ProgressUpdateObserver;
import com.ag04.clidemo.service.MockUserService;
import com.ag04.clidemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class UserServiceConfig {

    @Bean
    public UserService userService(ProgressUpdateObserver observer) throws IOException {
        MockUserService userService = new MockUserService();
        userService.setObserver(observer);
        userService.readFile("cli-users.json");
        return userService;
    }
}
