package com.ag04.clidemo.command;

import com.ag04.clidemo.model.CliUser;
import com.ag04.clidemo.model.Gender;
import com.ag04.clidemo.service.UserService;
import com.ag04.clidemo.shell.InputReader;
import com.ag04.clidemo.shell.ShellHelper;
import com.ag04.clidemo.shell.table.BeanTableModelBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import org.springframework.util.StringUtils;

import java.util.*;

@ShellComponent
public class UserCommand {

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    InputReader inputReader;

    @Autowired
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @ShellMethod("Display list of users")
    public void userList() {
        List<CliUser> users = userService.findAll();

        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("username", "Username");
        headers.put("fullName", "Full name");
        headers.put("gender", "Gender");
        headers.put("superuser", "Superuser");
        TableModel model = new BeanListTableModel<>(users, headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        shellHelper.print(tableBuilder.build().render(80));
    }

    @ShellMethod("Display details of user with supplied username")
    public void userDetails(@ShellOption({"-U", "--username"}) String username) {
        CliUser user = userService.findByUsername(username);
        displayUser(user);
    }


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

        // 3. Prompt for user's Gender ----------------------------------------------
        Map<String, String> options = new HashMap<>();
        options.put("M", Gender.MALE.name());
        options.put("F", Gender.FEMALE.name() );
        options.put("D", Gender.DIVERSE.name());

        String genderValue = inputReader.selectFromList("Gender", "Please enter one of the [] values", options, true, null);
        Gender gender = Gender.valueOf(options.get(genderValue.toUpperCase()));
        user.setGender(gender);

        // 4. Prompt for superuser attribute
        String superuserValue = inputReader.promptWithOptions("New user is superuser", "N", Arrays.asList("Y", "N"));
        if ("Y".equals(superuserValue)) {
            user.setSuperuser(true);
        } else {
            user.setSuperuser(false);
        }

        // Print user's input -------------------------------------------------
        shellHelper.printInfo("\nCreating a new user:");
        displayUser(user);

        CliUser createdUser = userService.create(user);
        shellHelper.printSuccess("---> SUCCESS created user with id=" + createdUser.getId());
    }

    @ShellMethod("Update and synchronize all users in local database with external source")
    public void updateAllUsers() {
        shellHelper.printInfo("Starting local user db update");
        long numOfUsers = userService.updateAll();
        String successMessage = shellHelper.getSuccessMessage("SUCCESS >>");
        successMessage = successMessage + String.format(" Total of %d local db users updated!", numOfUsers);
        shellHelper.print(successMessage);
    }

    private void displayUser(CliUser user) {
        LinkedHashMap<String, Object> labels = new LinkedHashMap<>();
        labels.put("id", "Id");
        labels.put("username", "Username");
        labels.put("fullName", "Full name");
        labels.put("gender", "Gender");
        labels.put("superuser", "Superuser");
        labels.put("password", "Password");

        /*
        Map<String, String> map = objectMapper.convertValue(entity, new TypeReference<Map<String, String>>() {});
        Object[][] entityProperties = new Object[map.size()+1][2];
        entityProperties[0][0] = "Property";
        entityProperties[0][1] = "Value";
        int i = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            entityProperties[i][0] = labels.get(entry.getKey())+ ":";
            entityProperties[i][1] = entry.getValue();
            i++;
        }
        TableModel model = new ArrayTableModel(entityProperties);
        */
        BeanTableModelBuilder builder = new BeanTableModelBuilder(user, objectMapper);
        TableModel model = builder.withLabels(labels).build();

        TableBuilder tableBuilder = new TableBuilder(model);

        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        tableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(20));
        tableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(30));
        shellHelper.print(tableBuilder.build().render(80));
    }

}
