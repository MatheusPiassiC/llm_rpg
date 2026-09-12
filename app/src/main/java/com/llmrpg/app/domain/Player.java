package com.llmrpg.app.domain;

import java.util.List;

public class Player {
    private String name;
    private Location currentLocation;
    private List<Relationship> relationships;
    private String personality;

    public Player(String name, Location currentLocation, List<Relationship> relationships, String personality) {
        this.name = name;
        this.currentLocation = currentLocation;
        this.relationships = relationships;
        this.personality = personality;
    }

    public String getName() {
        return name;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public String getPersonality() {
        return personality;
    }
}
