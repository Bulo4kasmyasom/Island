package ru.javarush.island.sternard.organisms;

import ru.javarush.island.sternard.organisms.parents.Herbivore;

import java.util.Map;

public class Deer extends Herbivore {
    public Deer(String name, double weight, int maxOnCell, String icon, String organismMainType, String organismType, Map<String, Integer> possibleFood, int speed, double energy, double maxFoodForSatiety) {
        super(name, weight, maxOnCell, icon, organismMainType, organismType, possibleFood, speed, energy, maxFoodForSatiety);
    }

}
