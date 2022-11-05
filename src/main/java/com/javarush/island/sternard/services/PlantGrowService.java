package com.javarush.island.sternard.services;

import com.javarush.island.sternard.controller.Controller;
import com.javarush.island.sternard.map.Cell;
import com.javarush.island.sternard.organisms.factory.OrganismFactory;
import com.javarush.island.sternard.organisms.parents.Organism;
import com.javarush.island.sternard.settings.Settings;
import com.javarush.island.sternard.utils.Randomizer;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class PlantGrowService {
    private final Controller controller;

    public Runnable createPlantGrowService() {
        return () -> {
            int w = Randomizer.get(controller.getWidth());
            int h = Randomizer.get(controller.getHeight());
            Cell cell = controller.getCells()[h][w];
            Map<String, String[]> organismType = Settings.get().getOrganismType();
            String[] plants = organismType.get("plant");
            String getRandomPlant = plants[Randomizer.get(plants.length)];
            Organism organism = OrganismFactory.createOrganism(getRandomPlant);
            for (int i = 0; i < Randomizer.get(organism.getMaxOnCell()); ++i) {
                cell.addOrganism(organism);
            }
        };
    }
}
