package com.javarush.island.sternard.actions;

import com.javarush.island.sternard.actions.interfaces.Reproducing;
import com.javarush.island.sternard.map.Cell;
import com.javarush.island.sternard.organisms.factory.OrganismFactory;
import com.javarush.island.sternard.organisms.parents.Animal;
import com.javarush.island.sternard.settings.Settings;
import com.javarush.island.sternard.utils.Randomizer;

import java.util.List;

import static com.javarush.island.sternard.controller.Controller.isSameOrganisms;

@SuppressWarnings({"UnusedDeclaration"}) // reflection api use this class and method
public class Reproduce implements Reproducing {
    public void action(Animal animal, Cell cell) {
        List<Animal> animalsInCell = cell.getAnimals();
        long similarAnimals = animalsInCell.stream()
                .filter(o -> isSameOrganisms(animal, o))
                .count();
        int reproduceChance = Settings.get().getActions().get("reproduce");
        int randomChanceToReproduce = Randomizer.get(100);
        if ((similarAnimals > 1) && (reproduceChance > randomChanceToReproduce)) {

            // Note:
            // best practice: need to use method clone, but my solution is:
            Animal newAnimal = (Animal) OrganismFactory.createOrganism(animal.getClass().getSimpleName());
            cell.addOrganism(newAnimal);
            //

            animal.reduceAnimalEnergy(animal);
        }
    }
}
