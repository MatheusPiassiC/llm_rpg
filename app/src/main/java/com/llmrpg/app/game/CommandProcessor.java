package com.llmrpg.app.game;

// Transforma texto em um comando estruturado que o jogo pode entender e processar
public class CommandProcessor {

    public Command processCommand(String input) {
        String normalized = input.trim().toLowerCase();

        if (normalized.equals("look")) {
            return new Command(CommandType.LOOK, null);
        }

        // if (normalized.equals("inventory")) {
        // return new Command(CommandType.INVENTORY, null);
        // }

        if (normalized.equals("help")) {
            return new Command(CommandType.HELP, null);
        }

        if (normalized.equals("quit")) {
            return new Command(CommandType.QUIT, null);
        }

        if (normalized.startsWith("go ")) {
            String target = normalized.substring(3).trim();

            return new Command(CommandType.MOVE, target);
        }

        if (normalized.startsWith("talk ")) {
            String target = normalized.substring(5).trim();

            return new Command(CommandType.TALK, target);
        }

        throw new IllegalArgumentException("Unknown command");
    }
}
