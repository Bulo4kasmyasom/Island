package com.javarush.island.sternard.actions;

import com.javarush.island.sternard.actions.interfaces.Reproducing;
import com.javarush.island.sternard.map.Cell;
import com.javarush.island.sternard.organisms.parents.Animal;
import com.javarush.island.sternard.settings.Settings;
import com.javarush.island.sternard.utils.Randomizer;

import java.util.List;
import java.util.Map;

import static com.javarush.island.sternard.controller.Controller.isSameOrganisms;

@SuppressWarnings({"UnusedDeclaration"}) // reflection api use this class and method
public class Reproduce implements Reproducing {
    public void action(Animal animal, Cell cell) {
        List<Animal> animalsInCell = cell.getAnimals();
        long similarAnimals = animalsInCell.stream()
                .filter(o -> isSameOrganisms(animal, o))
                .count();
        Map<String, Integer> actions = Settings.get().getActions();
        int reproduceChance = actions.get("reproduce");
        int randomChanceToReproduce = Randomizer.get(100);
        if ((similarAnimals > 1) && (reproduceChance > randomChanceToReproduce)) {

            Animal newAnimalClone = animal.clone(animal);
            cell.addOrganism(newAnimalClone);
            animal.reduceAnimalEnergy(animal);

        }
    }
}
