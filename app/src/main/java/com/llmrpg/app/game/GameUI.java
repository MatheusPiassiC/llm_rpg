package com.llmrpg.app.game;

public interface GameUI {
    boolean hasInput();

    String readInput();

    void showPrompt();

    void show(String message);
}
