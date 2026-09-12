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
            case INVENTORY -> inventory();
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

        output.append("\nLocais a vista:\n");
        for (Location visibleLocation : gameState.getWorld().getLocationsList()) {
            if (!visibleLocation.getId().equals(location.getId())) {
            output.append("- ")
                .append(visibleLocation.getName())
                .append("\n");
            }
        }

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
        Location destination = gameState.getWorld().getLocation(direction);
        if (destination == null) {
            return "Local nao encontrado: " + direction;
        }

        gameState.getPlayer().moveTo(destination);
        return "Voce entrou em " + destination.getName() + ".";
    }

    private String talk(String target) {
        return "Conversacao com NPC ainda nao foi implementada: " + target;
    }

    private String inventory() {
        return "Inventario vazio.";
    }

    private String help() {
        return "Comandos:\n"
            + "look - observar o local atual\n"
            + "go <local> - mover-se para um local\n"
            + "talk <npc> - conversar com um NPC\n"
            + "help - mostrar esta ajuda\n"
            + "quit - encerrar o jogo";
    }

    private String quit() {
        gameState.stop();
        return "Jogo encerrado.";
    }
}
