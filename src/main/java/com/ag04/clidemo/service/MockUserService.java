package com.ag04.clidemo.service;

import com.ag04.clidemo.model.CliUser;
import com.ag04.clidemo.observer.ProgressUpdateEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.lang.model.UnknownEntityException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Mock implementation of UserService.
 */
public class MockUserService extends Observable implements UserService {

    @Autowired
    private ObjectMapper objectMapper;
    private Observer observer;
    private List<CliUser> users = new ArrayList<>();

    @Override
    public CliUser findById(Long id) {
        for (CliUser user : users) {
            if (id.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public CliUser findByUsername(String username) {
        for (CliUser user : users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<CliUser> findAll() {
        return users;
    }

    @Override
    public boolean exists(String username) {
        for (CliUser user : users) {
            if (username.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CliUser create(CliUser user) {
        user.setId(new Long(getNextId()));
        users.add(user);
        return user;
    }

    @Override
    public CliUser update(CliUser user) {
        for(CliUser u : users) {
            if (u.getId().equals(user.getId())) {
                u = user;
                return user;
            }
        }
        throw new IllegalArgumentException("No matching user found!");
    }

    @Override
    public long updateAll() {
        long numberOfUsers = 2000;
        for (long i = 1; i <= numberOfUsers; i++) {
            // do some operation ...
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // notify observer of the change
            if (observer != null) {
                String message = "";
                if (i < numberOfUsers) {
                    message = ":: please WAIT update operation in progress";
                }
                observer.update(
                        this,
                        new ProgressUpdateEvent(i, numberOfUsers, message)
                );
            }
        }
        return numberOfUsers;
    }

    //--- set / get methods ---------------------------------------------------

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //--- util methods --------------------------------------------------------

    public void init(String filePath) throws IOException {
        ClassPathResource cpr = new ClassPathResource("cli-users.json");
        users = objectMapper.readValue(cpr.getInputStream(), new TypeReference<List<CliUser>>() { });
    }

    private long getNextId() {
        long maxId = 0;
        for(CliUser user : users) {
            if (user.getId().longValue() > maxId) {
                maxId = user.getId().longValue();
            }
        }
        return maxId + 1;
    }
}
