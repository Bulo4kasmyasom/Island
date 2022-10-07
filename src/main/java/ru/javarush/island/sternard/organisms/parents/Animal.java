package ru.javarush.island.sternard.organisms.parents;

import ru.javarush.island.sternard.settings.Settings;

import java.util.Map;

public class Animal extends Organism {

    public Animal(String name, double weight, int maxOnCell, String icon, String organismMainType, String organismType, Map<String, Integer> possibleFood, int speed, double energy, double maxFoodForSatiety) {
        super(name, weight, maxOnCell, icon, organismMainType, organismType, possibleFood, speed, energy, maxFoodForSatiety);
    }

    public double reduceAnimalEnergy(Animal animal) {
        double reduceEnergyPercent = Settings.get().getReduceEnergyPercent();
//        return animal.getEnergy() - ((animal.getEnergy() / 100) * reduceEnergyPercent);
        return animal.getEnergy() - reduceEnergyPercent;
    }

    public double increaseAnimalEnergy(Animal animal) {
        double increaseEnergyPercent = Settings.get().getIncreaseEnergyPercent();
//        return ((animal.getEnergy() / 100) * increaseEnergyPercent) + animal.getEnergy();
        return increaseEnergyPercent + animal.getEnergy();
    }

}
