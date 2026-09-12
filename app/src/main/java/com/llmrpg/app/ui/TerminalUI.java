package com.llmrpg.app.ui;

import java.io.PrintStream;
import java.util.Scanner;

import com.llmrpg.app.game.GameUI;

public class TerminalUI implements GameUI {
    private final Scanner input;
    private final PrintStream output;

    public TerminalUI(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean hasInput() {
        return input.hasNextLine();
    }

    @Override
    public String readInput() {
        return input.nextLine();
    }

    @Override
    public void showPrompt() {
        output.print("> ");
    }

    @Override
    public void show(String message) {
        if (message != null && !message.isBlank()) {
            output.println(message);
        }
    }
}
