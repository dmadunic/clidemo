package com.ag04.clidemo.command;

import com.ag04.clidemo.shell.ProgressBar;
import com.ag04.clidemo.shell.ShellHelper;
import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class EchoCommand {

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    ProgressBar progressBar;


    @ShellMethod("Displays greeting message to the user whose name is supplied")
    public String echo(@ShellOption({"-N", "--name"}) String name) {
        String message = String.format("Hello %s!", name);
        shellHelper.print(message.concat(" (Default style message)"));
        shellHelper.printError(message.concat(" (Error style message)"));
        shellHelper.printWarning(message.concat(" (Warning style message)"));
        shellHelper.printInfo(message.concat(" (Info style message)"));
        shellHelper.printSuccess(message.concat(" (Success style message)"));

        String output = shellHelper.getSuccessMessage(message);
        return output.concat(" You are running spring shell cli-demo.");
    }

    @ShellMethod("Displays progress")
    public void progress() throws InterruptedException {
        for (int i = 1; i <=100; i++) {
            progressBar.display(i);
            Thread.sleep(200);
        }
        shellHelper.getTerminal().writer().println();
    }

}
