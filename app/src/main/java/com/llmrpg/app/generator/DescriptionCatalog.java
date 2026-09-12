package com.llmrpg.app.generator;

// Representa um catálogo de descrições para diferentes elementos do jogo
import java.util.List;

public class DescriptionCatalog {

    private DescriptionCatalog() {
    }

    public static List<String> locationOpenings() {
        return List.of(
                "Uma praça movimentada",
                "Um caminho estreito",
                "Uma construção antiga",
                "Uma área silenciosa");
    }

    public static List<String> locationDetails() {
        return List.of(
                "cercada por casas de pedra.",
                "onde comerciantes discutem os preços do dia.",
                "marcada por sinais de uma guerra antiga.",
                "iluminada por lanternas de ferro.");
    }

    public static String buildLocationDescription(
            String opening,
            String detail) {

        return opening + " " + detail;
    }
}