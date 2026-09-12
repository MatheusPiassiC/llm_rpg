package com.llmrpg.app.game;

import com.llmrpg.app.domain.Location;
import com.llmrpg.app.domain.NPC;
import com.llmrpg.app.domain.Player;

//Executa os comandos do jogo, atualizando o estado do jogo e gerando saídas apropriadas
public class GameEngine {
    private final GameState gameState;

    public GameEngine(GameState gameState) {
        this.gameState = gameState;
    }

    public String execute(Command command) {

        return switch (command.getType()) {
            case LOOK -> look();
            case MOVE -> move(command.getArgument());
            case TALK -> talk(command.getArgument());
            // case INVENTORY -> inventory();
            case HELP -> help();
            case QUIT -> quit();
        };
    }

    private String look() {
        Player player = gameState.getPlayer();
        Location location = player.getCurrentLocation();

        StringBuilder output = new StringBuilder();

        output.append(location.getName())
                .append("\n\n");

        output.append(location.getDescription())
                .append("\n");

        // NPCs
        if (!location.getNpcs().isEmpty()) {
            output.append("\nPeople here:\n");

            for (NPC npc : location.getNpcs()) {
                output.append("- ")
                        .append(npc.getName())
                        .append("\n");
            }
        }

        return output.toString();
    }

    private String move(String direction) {
        return null;
    }

    private String talk(String target) {
        return null;
    }

    private String help() {
        return null;
    }

    private String quit() {
        gameState.stop();
        return "Jogo encerrado.";
    }
}
