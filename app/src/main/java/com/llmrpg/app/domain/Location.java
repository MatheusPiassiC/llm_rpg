package com.llmrpg.app.domain;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String id;
    private String name;
    private String description;
    private List<NPC> npcs;
    private List<Structure> structures;

    public Location(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.npcs = new ArrayList<>();
        this.structures = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public NPC getNpc(String npcId) {
        for (NPC npc : npcs) {
            if (npc.getId().equals(npcId)) {
                return npc;
            }
        }
        return null;
    }

    public void addNpc(NPC npc) {
        this.npcs.add(npc);
    }

    public NPC removeNpc(String npcId) {
        for (NPC npc : npcs) {
            if (npc.getId().equals(npcId)) {
                npcs.remove(npc);
                return npc;
            }
        }
        return null;
    }

    public List<Structure> getStructures() {
        return structures;
    }
}
