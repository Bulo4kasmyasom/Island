package ru.javarush.island.sternard.organisms;

import ru.javarush.island.sternard.organisms.parents.Herbivore;

import java.util.Map;

public class Boar extends Herbivore {
    public Boar(String name, double weight, int maxOnCell, String icon, String organismType, Map<String, Integer> possibleFood, int speed, double energy, double maxFoodForSatiety) {
        super(name, weight, maxOnCell, icon, organismType, possibleFood, speed, energy, maxFoodForSatiety);
    }
}
