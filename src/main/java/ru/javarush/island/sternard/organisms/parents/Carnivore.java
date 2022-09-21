package ru.javarush.island.sternard.organisms.parents;

import java.util.Map;

public abstract class Carnivore extends Animal {
    public Carnivore(String name, double weight, int maxOnCell, String icon, String organismType, Map<String, Integer> possibleFood, int speed, double energy, double maxFoodForSatiety) {
        super(name, weight, maxOnCell, icon, organismType, possibleFood, speed, energy, maxFoodForSatiety);
    }
}
