package ru.javarush.island.sternard.actions;

import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.settings.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ru.javarush.island.sternard.controller.Controller.isSameOrganisms;

@SuppressWarnings({"UnusedDeclaration"}) // reflection api use this class and method
public class Eat {
    public void action(Animal animal, Cell cell) {
        List<Organism> possibleFoodInCellStream = cell.getOrganisms().stream()
                .filter(o -> !isSameOrganisms(animal, o))
                .toList();
        List<Organism> foodList = new ArrayList<>(possibleFoodInCellStream);

        if (!foodList.isEmpty()) {
            Iterator<Organism> iter = foodList.iterator();
            while (iter.hasNext()) {
                Organism food = iter.next();
                if (animal.getPossibleFood().containsKey(food.getName())) {
                    this.tryToEat(animal, cell, food);
                    iter.remove();
                }
            }
        } else {
            double reduceAnimalEnergy = animal.reduceAnimalEnergy(animal);
            animal.setEnergy(reduceAnimalEnergy);
        }

    }

    private void tryToEat(Animal animal, Cell cell, Organism foodOrganism) {
        int getPossibleChance = animal.getPossibleFood().get(foodOrganism.getName());
        Integer eatChance = Settings.get().getActions().get("eat");
        if (getPossibleChance < eatChance) {
            double fullSatiety = animal.getWeight() + animal.getMaxFoodForSatiety();
            if (foodOrganism.getWeight() >= animal.getMaxFoodForSatiety()) {
                animal.setWeight(fullSatiety);
                double increaseAnimalEnergy = animal.increaseAnimalEnergy(animal);
                animal.setEnergy(increaseAnimalEnergy);
            } else {
                double notSatiatedButEats = animal.getWeight() + foodOrganism.getWeight();
                animal.setWeight(Math.min(notSatiatedButEats, fullSatiety));
            }
            cell.removeOrganism(foodOrganism);
        }
    }
}
