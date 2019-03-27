package com.ag04.clidemo.service;

import com.ag04.clidemo.model.CliUser;

import java.util.List;

/**
 * Interface describing (Cli) UserService.
 *
 */
public interface UserService {
    CliUser findById(Long id);
    CliUser findByUsername(String username);
    List<CliUser> findAll();

    boolean exists(String username);
    CliUser create(CliUser user);
    CliUser update(CliUser user);
    long updateAll();
}
