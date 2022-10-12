package ru.javarush.island.sternard.services;

import lombok.AllArgsConstructor;
import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.map.Cell;
import ru.javarush.island.sternard.organisms.factory.OrganismFactory;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.settings.Settings;
import ru.javarush.island.sternard.utils.Randomizer;

@AllArgsConstructor
public class PlantGrowService {
    private final Controller controller;

    public Runnable createPlantGrowService() {
        return () -> {
            int w = Randomizer.get(controller.getWidth());
            int h = Randomizer.get(controller.getHeight());
            Cell cell = controller.getCells()[h][w];
            String[] plants = Settings.get().getOrganismType().get("plant");
            String getRandomPlant = plants[Randomizer.get(plants.length)];
            Organism organism = OrganismFactory.createOrganism(getRandomPlant);
            for (int i = 0; i < Randomizer.get(organism.getMaxOnCell()); ++i) {
                cell.addOrganism(organism);
            }
        };
    }
}
