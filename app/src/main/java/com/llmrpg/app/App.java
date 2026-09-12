package com.llmrpg.app;

import java.util.ArrayList;
import java.util.Scanner;

import com.llmrpg.app.domain.Location;
import com.llmrpg.app.domain.NPC;
import com.llmrpg.app.domain.Player;
import com.llmrpg.app.domain.World;
import com.llmrpg.app.game.CommandProcessor;
import com.llmrpg.app.game.Game;
import com.llmrpg.app.game.GameState;
import com.llmrpg.app.game.GameUI;
import com.llmrpg.app.generator.WorldGenerator;
import com.llmrpg.app.ui.TerminalUI;

public class App {
    public static void main(String[] args) {
        WorldGenerator worldGenerator = new WorldGenerator();

        World generatedWorld = worldGenerator.generate(12345);

        Player player = new Player(
                "Aventureiro",
                generatedWorld.getLocationsList().get(0),
                new ArrayList<>(),
                "Curioso");

        GameState gameState = new GameState(generatedWorld, player);
        Game game = new Game(gameState, new CommandProcessor());

        try (Scanner input = new Scanner(System.in)) {
            GameUI terminal = new TerminalUI(input, System.out);
            game.start(terminal);
        }
    }
}
