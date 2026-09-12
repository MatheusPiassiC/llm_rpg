package com.llmrpg.app.domain;

public class Relationship {
    private String targetId;
    private String description;

    public Relationship(String targetId, String description) {
        this.targetId = targetId;
        this.description = description;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getDescription() {
        return description;
    }
}
