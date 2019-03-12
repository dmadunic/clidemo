package com.ag04.clidemo.command;

import com.ag04.clidemo.model.CliUser;
import com.ag04.clidemo.service.UserService;
import com.ag04.clidemo.shell.InputReader;
import com.ag04.clidemo.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

@ShellComponent
public class UserCommand {

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    InputReader inputReader;

    @Autowired
    UserService userService;

    @ShellMethod("Create new user with supplied username")
    public void createUser(@ShellOption({"-U", "--username"}) String username) {
        if (userService.exists(username)) {
            shellHelper.printError(String.format("User with username='%s' already exists --> ABORTING", username));
            return;
        }
        CliUser user = new CliUser();
        user.setUsername(username);

        shellHelper.printInfo("Please enter new user data:");
        // 1. read user's fullName --------------------------------------------
        do {
            String fullName = inputReader.prompt("Full name");
            if (StringUtils.hasText(fullName)) {
                user.setFullName(fullName);
            } else {
                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
            }
        } while (user.getFullName() == null);

        // 2. read user's password --------------------------------------------
        do {
            String password = inputReader.prompt("Password", "secret", false);
            if (StringUtils.hasText(password)) {
                user.setPassword(password);
            } else {
                shellHelper.printWarning("Password'CAN NOT be empty string? Please enter valid value!");
            }
        } while (user.getPassword() == null);

        // Print user's input -------------------------------------------------
        shellHelper.printInfo("\nNew user data:");
        shellHelper.print("Username: " + user.getUsername());
        shellHelper.print("Password: " + user.getPassword());
        shellHelper.print("Fullname: " + user.getFullName());
        shellHelper.print("Gender: " + user.getGender());
        shellHelper.print("Superuser: " + user.isSuperuser() + "\n");

        CliUser createdUser = userService.create(user);
        shellHelper.printSuccess("---> SUCCESS created user with id=" + createdUser.getId());
    }
}
