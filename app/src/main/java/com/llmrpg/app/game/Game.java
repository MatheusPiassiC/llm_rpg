package com.llmrpg.app.game;

import java.util.Scanner;

// Controla o loop principal do jogo, gerenciando o estado do jogo e a interação com o jogador
public class Game {
    private GameState gameState;
    private CommandProcessor commandProcessor;
    private GameEngine gameEngine;

    public Game(GameState gameState, CommandProcessor commandProcessor) {
        this.gameState = gameState;
        this.commandProcessor = commandProcessor;
        this.gameEngine = new GameEngine(gameState);
    }

    public void start(Scanner input) {
        while (gameState.isRunning() && input.hasNextLine()) {
            String text = input.nextLine();
            Command command = commandProcessor.processCommand(text);
            String output = gameEngine.execute(command);

            System.out.println(output);
        }
    }
}
