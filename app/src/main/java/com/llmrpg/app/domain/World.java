package com.llmrpg.app.domain;

import java.util.ArrayList;
import java.util.List;

public class World {
    private String seed;
    private String name;
    private List<Location> location;

    public World(String seed, String name) {
        this.seed = seed;
        this.name = name;
        this.location = new ArrayList<>();
    }

    public Location getLocation(String name) {
        for (Location loc : location) {
            if (loc.getName().equals(name)) {
                return loc;
            }
        }
        return null;
    }

    public void addLocation(Location location) {
        this.location.add(location);
    }

    public String getSeed() {
        return seed;
    }

    public String getName() {
        return name;
    }

    public List<Location> getLocationsList() {
        return location;
    }
}
