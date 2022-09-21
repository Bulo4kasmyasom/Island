package ru.javarush.island.sternard.organisms.parents;

public abstract class Plant extends Organism {
    public Plant(String name, double weight, int maxOnCell, String icon, String organismType) {
        super(name, weight, maxOnCell, icon, organismType);
    }
}
