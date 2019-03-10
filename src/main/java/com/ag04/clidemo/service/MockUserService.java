package com.ag04.clidemo.service;

import com.ag04.clidemo.model.CliUser;
import org.springframework.stereotype.Service;

/**
 * Mock implementation of UserService.
 *
 */
@Service
public class MockUserService implements UserService {

    @Override
    public boolean exists(String username) {
        if ("admin".equals(username)) {
            return true;
        }
        return false;
    }

    @Override
    public CliUser create(CliUser user) {
        user.setId(10000L);
        return user;
    }

    @Override
    public CliUser update(CliUser user) {
        return user;
    }
}
