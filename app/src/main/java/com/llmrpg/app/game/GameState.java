package com.llmrpg.app.game;

import com.llmrpg.app.domain.Player;
import com.llmrpg.app.domain.World;

public class GameState {
    private final World world;
    private final Player player;
    private boolean running;

    public GameState(World world, Player player) {
        this.world = world;
        this.player = player;
        this.running = true;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }
}
