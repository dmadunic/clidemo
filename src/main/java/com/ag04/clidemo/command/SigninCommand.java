package com.ag04.clidemo.command;

import com.ag04.clidemo.shell.InputReader;
import com.ag04.clidemo.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class SigninCommand extends SecuredCommand {

    @Lazy
    @Autowired
    ShellHelper shellHelper;

    @Lazy
    @Autowired
    InputReader inputReader;

    @Autowired
    AuthenticationManager authenticationManager;

    @ShellMethod("Sign in as clidemo user")
    public void signIn() {
        String username;
        boolean usernameInvalid = true;
        do {
            username = inputReader.prompt("Please enter your username");
            if (StringUtils.hasText(username)) {
                usernameInvalid = false;
            } else {
                shellHelper.printWarning("Username can not be empty string!");
            }
        } while (usernameInvalid);
        String password = inputReader.prompt("Please enter your password", null, false);
        Authentication request = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            shellHelper.printSuccess("User: " + username + " successfully authenticated! --> Welcome to CliDemo.");
        } catch (AuthenticationException e) {
            shellHelper.printWarning("Authentication failed: " + e.getMessage());
        }
    }

}
