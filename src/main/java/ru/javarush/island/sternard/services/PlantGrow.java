package ru.javarush.island.sternard.services;

import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.game.OrganismFactory;
import ru.javarush.island.sternard.settings.Settings;
import ru.javarush.island.sternard.utils.Randomizer;

public class PlantGrow {
    private final Controller controller;

    public PlantGrow(Controller controller) {
        this.controller = controller;
    }

    public Runnable createPlantGrowTask() {
        return () -> {
            int x = Randomizer.get(controller.getWidth());
            int y = Randomizer.get(controller.getHeight());
            Cell cell = controller.getCells()[y][x];
            String[] plants = Settings.get().getOrganismType().get("plant");
            String getRandomPlant = plants[Randomizer.get(plants.length)];
            cell.addOrganism(OrganismFactory.createOrganism(getRandomPlant));
        };
    }
}
