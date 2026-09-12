package com.llmrpg.app.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.llmrpg.app.domain.Location;
import com.llmrpg.app.domain.NPC;
import com.llmrpg.app.domain.World;

public class WorldGenerator {

    public World generate(long seed) {
        Random random = new Random(seed);

        String cityName = choose(
                random,
                List.of("Valdora", "Eldermoor", "Ravenford"));

        World world = new World(Long.toString(seed), cityName);

        List<Location> locations = createLocations(random);
        locations.forEach(world::addLocation);

        addNpcs(random, locations);

        return world;
    }

    private List<Location> createLocations(Random random) {
        List<LocationTemplate> templates = new ArrayList<>(List.of(
                new LocationTemplate(
                        "square",
                        "Praça Central",
                        "Uma praça aberta no centro da cidade."),

                new LocationTemplate(
                        "tavern",
                        "Taverna",
                        "Um estabelecimento cheio de conversas e rumores."),

                new LocationTemplate(
                        "forge",
                        "Ferraria",
                        "O calor da forja ilumina as ferramentas e armas."),

                new LocationTemplate(
                        "shrine",
                        "Santuário",
                        "Um pequeno santuário cercado por velas antigas."),

                new LocationTemplate(
                        "market",
                        "Mercado",
                        "Bancas coloridas ocupam a rua principal.")));

        Collections.shuffle(templates, random);

        int locationCount = 3 + random.nextInt(3);
        List<Location> locations = new ArrayList<>();

        for (int index = 0; index < locationCount; index++) {
            LocationTemplate template = templates.get(index);

            String description = generateDescription(random);

            locations.add(new Location(
                    template.id(),
                    template.name(),
                    description));
        }

        return locations;
    }

    private String generateDescription(Random random) {
        String opening = choose(
                random,
                DescriptionCatalog.locationOpenings());

        String detail = choose(
                random,
                DescriptionCatalog.locationDetails());

        return DescriptionCatalog.buildLocationDescription(
                opening,
                detail);
    }

    private void addNpcs(Random random, List<Location> locations) {
        List<String> availableNames = new ArrayList<>(NPCCatalog.names());

        int npcCount = Math.min(
                4 + random.nextInt(2),
                availableNames.size());

        for (int index = 0; index < npcCount; index++) {
            String name = chooseAndRemove(random, availableNames);

            Location location = locations.get(
                    random.nextInt(locations.size()));

            NPC npc = new NPC(
                    name.toLowerCase(),
                    name,
                    choose(random, NPCCatalog.occupations()),
                    choose(random, NPCCatalog.personalities()),
                    choose(random, NPCCatalog.backstories()),
                    choose(random, NPCCatalog.appearances()),
                    choose(random, NPCCatalog.knowledge()));

            location.addNpc(npc);
        }
    }

    private String chooseAndRemove(
            Random random,
            List<String> values) {

        int index = random.nextInt(values.size());
        return values.remove(index);
    }

    private String choose(Random random, List<String> values) {
        return values.get(random.nextInt(values.size()));
    }
}
