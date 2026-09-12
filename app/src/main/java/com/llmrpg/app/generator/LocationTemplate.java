package com.llmrpg.app.generator;

// Representa uma possibilidade de local. A instância final sera um Location de fato
public record LocationTemplate(
        String id,
        String name,
        String description) {
}