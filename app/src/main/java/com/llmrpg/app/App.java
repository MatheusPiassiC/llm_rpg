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
import com.llmrpg.app.ui.TerminalUI;

public class App {
    public static void main(String[] args) {
        World world = new World("demo-seed", "Valdora");
        Location square = new Location(
                "square",
                "Praca central",
                "Uma pequena praca cercada por casas antigas.");
        Location tavern = new Location(
                "tavern",
                "Taverna",
                "Uma taverna movimentada, cheia de conversas e rumores.");
        Location forge = new Location(
                "forge",
                "Ferraria",
                "O calor da forja ilumina as ferramentas e as armas em producao.");

        NPC blacksmith = new NPC(
                "aldren",
                "Aldren",
                "Ferreiro",
                "Reservado e orgulhoso",
                "Aldren trabalhou para o exercito durante a Guerra do Norte.",
                "Um homem forte com um avental de couro.",
                "Conhece os segredos da vila.");
        square.addNpc(blacksmith);
        world.addLocation(square);
        world.addLocation(tavern);
        world.addLocation(forge);

        Player player = new Player(
                "Aventureiro",
                square,
                new ArrayList<>(),
                "Curioso");

        GameState gameState = new GameState(world, player);
        Game game = new Game(gameState, new CommandProcessor());

        try (Scanner input = new Scanner(System.in)) {
            GameUI terminal = new TerminalUI(input, System.out);
            game.start(terminal);
        }
    }
}
