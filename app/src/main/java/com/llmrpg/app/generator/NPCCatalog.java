package com.llmrpg.app.generator;

import java.util.List;

// Representa um catálogo de NPCs, com nomes, ocupações, personalidades, histórias de fundo e conhecimentos.
public class NPCCatalog {

    private NPCCatalog() {
    }

    public static List<String> names() {
        return List.of(
                "Aldren",
                "Mira",
                "Tomas",
                "Elian",
                "Garrick");
    }

    public static List<String> occupations() {
        return List.of(
                "Ferreiro",
                "Taverneira",
                "Guarda",
                "Curandeiro",
                "Comerciante");
    }

    public static List<String> personalities() {
        return List.of(
                "Reservado e orgulhoso",
                "Acolhedora, mas desconfiada",
                "Sério e observador",
                "Curioso e falante",
                "Nervoso e supersticioso");
    }

    public static List<String> backstories() {
        return List.of(
                "Vive nesta cidade desde a infância.",
                "Chegou à cidade depois de uma longa viagem.",
                "Serviu durante uma guerra antes de se estabelecer aqui.",
                "Herdou seu estabelecimento de um parente.");
    }

    public static List<String> appearances() {
        return List.of(
                "Forte com um avental de couro.",
                "Meia-idade com olhos atentos.",
                "Jovem com cicatrizes de batalha.",
                "Uma pessoa idosa com cabelos grisalhos e mãos calejadas.");
    }

    public static List<String> knowledge() {
        return List.of(
                "Conhece os rumores da cidade.",
                "Conhece bem os comerciantes locais.",
                "Sabe quais caminhos levam para fora da cidade.",
                "Ouviu histórias sobre os túneis sob a cidade.");
    }
}