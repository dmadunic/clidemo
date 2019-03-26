package com.ag04.clidemo.shell;

import org.jline.terminal.Terminal;

public class ProgressBar {
    private String doneMarker = "=";
    private String remainsMarker = "-";
    private String leftDelimiter = "<";
    private String rightDelimiter = ">";

    ShellHelper shellHelper;

    public ProgressBar(ShellHelper shellHelper) {
        this.shellHelper = shellHelper;
    }

    public void display(int percentage) {
        display(percentage, null);
    }

    public void display(int percentage, String statusMessage) {
        int x = (percentage/5);
        int y = 20-x;
        String message = ((statusMessage == null) ? "" : statusMessage);

        String done = shellHelper.getSuccessMessage(new String(new char[x]).replace("\0", doneMarker));
        String remains = new String(new char[y]).replace("\0", remainsMarker);

        String progressBar = String.format("%s%s%s%s %d", leftDelimiter, done, remains, rightDelimiter, percentage);

        shellHelper.getTerminal().writer().print("\r" + progressBar + "% " + message);
        shellHelper.getTerminal().flush();
    }

    //--- set / get methods ---------------------------------------------------

    public String getDoneMarker() {
        return doneMarker;
    }

    public void setDoneMarker(String doneMarker) {
        this.doneMarker = doneMarker;
    }

    public String getRemainsMarker() {
        return remainsMarker;
    }

    public void setRemainsMarker(String remainsMarker) {
        this.remainsMarker = remainsMarker;
    }

    public String getLeftDelimiter() {
        return leftDelimiter;
    }

    public void setLeftDelimiter(String leftDelimiter) {
        this.leftDelimiter = leftDelimiter;
    }

    public String getRightDelimiter() {
        return rightDelimiter;
    }

    public void setRightDelimiter(String rightDelimiter) {
        this.rightDelimiter = rightDelimiter;
    }

}
