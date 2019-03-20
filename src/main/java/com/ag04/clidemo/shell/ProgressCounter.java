package com.ag04.clidemo.shell;

import org.jline.terminal.Terminal;

/**
 *
 */
public class ProgressCounter {
    private Terminal terminal;
    private char[] spinner = {'|', '/', '-', '\\'};

    private String pattern = " %s: %d ";

    private int spinCounter = 0;

    public ProgressCounter(Terminal terminal) {
        this(terminal, null);
    }

    public ProgressCounter(Terminal terminal, String pattern) {
        this(terminal, pattern, null);
    }

    public ProgressCounter(Terminal terminal, String pattern, char[] spinner) {
        this.terminal = terminal;

        if (pattern != null) {
            this.pattern = pattern;
        }
        if (spinner != null) {
            this.spinner = spinner;
        }
    }

    public void display(int count, String message) {
        String progress = String.format(pattern, message, count);

        terminal.writer().print("\r" + getSpinnerChar() + progress);
        terminal.flush();
    }

    public void display() {
        terminal.writer().print("\r" + getSpinnerChar());
        terminal.flush();
    }

    public void reset() {
        spinCounter = 0;
    }

    private char getSpinnerChar() {
        char spinChar = spinner[spinCounter];
        spinCounter++;
        if (spinCounter == spinner.length) {
            spinCounter = 0;
        }
        return spinChar;
    }

    //--- set / get methods ---------------------------------------------------

    public char[] getSpinner() {
        return spinner;
    }

    public void setSpinner(char[] spinner) {
        this.spinner = spinner;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
