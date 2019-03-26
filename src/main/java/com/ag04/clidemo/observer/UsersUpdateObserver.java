package com.ag04.clidemo.observer;

import com.ag04.clidemo.shell.ProgressBar;
import com.ag04.clidemo.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Observer;

@Service
public class UsersUpdateObserver implements Observer {

    @Autowired
    private ProgressBar progressBar;

    @Autowired
    private ShellHelper shellHelper;

    @Override
    public void update(Observable observable, Object event) {
        ProgressUpdateEvent upe = (ProgressUpdateEvent) event;
        int currentRecord = upe.getCurrentCount().intValue();
        int totalRecords = upe.getTotalCount().intValue();

        int percentage = currentRecord * 100 / totalRecords;
        String message = shellHelper.getWarningMessage(":: please WAIT update operation in progress");
        progressBar.display(percentage, message);

        if (currentRecord == totalRecords) {
            shellHelper.getTerminal().writer().println();
        }
    }
}
