package ru.javarush.island.sternard.actions;

import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.settings.Settings;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ru.javarush.island.sternard.controller.Controller.isSameOrganisms;

@SuppressWarnings({"UnusedDeclaration"}) // reflection api used this class and method
public class Eat {
    public void action(Animal animal, Cell cell) {
        List<Organism> organisms = cell.getOrganisms();
        List<Organism> possibleFood = organisms.stream()
                .filter(o -> !isSameOrganisms(animal, o))
                .toList();
        if (!possibleFood.isEmpty()) {
            int index = ThreadLocalRandom.current().nextInt(possibleFood.size());
            Organism foodOrganism = possibleFood.get(index);
            this.tryToEat(animal, cell, foodOrganism);
        } else {
            double reduceAnimalEnergy = animal.reduceAnimalEnergy(animal);
            animal.setEnergy(reduceAnimalEnergy);
        }
    }

    private void tryToEat(Animal animal, Cell cell, Organism foodOrganism) {
        if (animal.getPossibleFood().containsKey(foodOrganism.getName())) {
            int getPossibleChance = animal.getPossibleFood().get(foodOrganism.getName());
//            int eatChance = ThreadLocalRandom.current().nextInt(100);
            Integer eatChance = Settings.get().getActions().get("eat");
            if (getPossibleChance < eatChance) {
                double fullSatiety = animal.getWeight() + animal.getMaxFoodForSatiety();
                if (foodOrganism.getWeight() >= animal.getMaxFoodForSatiety()) {
                    animal.setWeight(fullSatiety);
                    double increaseAnimalEnergy = animal.increaseAnimalEnergy(animal);
                    animal.setEnergy(increaseAnimalEnergy);
                } else {
                    double notSatiatedButEats = animal.getWeight() + foodOrganism.getWeight();
                    animal.setWeight(Math.min(notSatiatedButEats, (fullSatiety)));
                }
                cell.removeOrganism(foodOrganism);
            }
        }
    }
}
