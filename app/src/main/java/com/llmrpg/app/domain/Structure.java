package com.llmrpg.app.domain;

public class Structure {
    private String name;
    private String description;

    public Structure(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
