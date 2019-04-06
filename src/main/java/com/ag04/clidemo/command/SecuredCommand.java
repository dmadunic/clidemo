package com.ag04.clidemo.command;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

public abstract class SecuredCommand {

    @ShellMethodAvailability({"user-list", "my-details"})
    public Availability isUserSignedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication instanceof  UsernamePasswordAuthenticationToken)) {
            return Availability.unavailable("you are not signedIn. Please sign in to be able to use this command!");
        }
        return Availability.available();
    }

    @ShellMethodAvailability({"create-user", "update-all-users", "user-details"})
    public Availability isUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication instanceof  UsernamePasswordAuthenticationToken)) {
            return Availability.unavailable("you are not signedIn. Please sign in to be able to use this command!");
        }
        if (!authentication.getAuthorities().contains("ROLE_ADMIN")) {
            return Availability.unavailable("you have insufficient privileges to run this command!");
        }
        return Availability.available();
    }
}
