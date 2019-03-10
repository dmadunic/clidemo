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

        //--- read user's fullName --------------------------------------------
        do {
            String fullName = inputReader.prompt("Full name");
            if (StringUtils.isEmpty(fullName)) {
                shellHelper.printWarning("User's full name CAN NOT be empty string? Please enter valid value!");
            } else {
                user.setFullName(fullName);
            }
        } while (user.getFullName() == null);

        shellHelper.print("Creating user: " + user);

    }
}
