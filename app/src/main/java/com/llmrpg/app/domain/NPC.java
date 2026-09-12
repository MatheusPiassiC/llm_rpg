package com.llmrpg.app.domain;

import java.util.ArrayList;
import java.util.List;

public class NPC {
    private String id;
    private String name;
    private String occupation;
    private String personality;
    private String backstory;
    private String appearance;
    private String knowledge;
    private List<Relationship> relationships;

    public NPC(String id, String name, String occupation, String personality, String backstory, String appearance,
            String knowledge) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
        this.personality = personality;
        this.backstory = backstory;
        this.appearance = appearance;
        this.knowledge = knowledge;
        this.relationships = new ArrayList<Relationship>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getPersonality() {
        return personality;
    }

    public String getBackstory() {
        return backstory;
    }

    public String getAppearance() {
        return appearance;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void addRelationship(Relationship relationship) {
        this.relationships.add(relationship);
    }

    public Relationship getRelationship(String npcId) {
        for (Relationship relationship : relationships) {
            if (relationship.getTargetId().equals(npcId)) {
                return relationship;
            }
        }

        return null;
    }
}
