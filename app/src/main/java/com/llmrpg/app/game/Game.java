package com.llmrpg.app.game;

public class Game {
    private final GameState gameState;
    private final CommandProcessor commandProcessor;
    private final GameEngine gameEngine;

    public Game(GameState gameState, CommandProcessor commandProcessor) {
        this.gameState = gameState;
        this.commandProcessor = commandProcessor;
        this.gameEngine = new GameEngine(gameState);
    }

    public boolean isRunning() {
        return gameState.isRunning();
    }

    public String process(String input) {
        try {
            Command command = commandProcessor.processCommand(input);
            return gameEngine.execute(command);
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
    }

    public void start(GameUI ui) {
        ui.show("Bem-vindo a " + gameState.getWorld().getName() + "!");
        ui.show("Digite help para ver os comandos.");
        ui.show(process("look"));

        while (isRunning() && ui.hasInput()) {
            ui.showPrompt();
            ui.show(process(ui.readInput()));
        }
    }
}
