package com.ag04.clidemo.service;

import com.ag04.clidemo.model.CliUser;

/**
 * Interface describing (Cli) UserService.
 *
 */
public interface UserService {

    boolean exists(String username);

    CliUser create(CliUser user);

    CliUser update(CliUser user);
}
