package ru.javarush.island.sternard.organisms.parents;

import lombok.Getter;
import java.util.Map;

@SuppressWarnings("unused") // private Organism() supressed
@Getter
public class Organism {
    protected String name;
    protected double weight;
    protected int maxOnCell;
    protected String icon;
    protected String organismMainType;
    protected String organismType;
    protected int speed;
    protected double energy;
    protected double maxFoodForSatiety;
    protected Map<String, Integer> possibleFood;

    private Organism() {
    }

    public Organism(String name, double weight, int maxOnCell, String icon, String organismMainType, String organismType) {
        this.name = name;
        this.weight = weight;
        this.maxOnCell = maxOnCell;
        this.icon = icon;
        this.organismType = organismType;
        this.organismMainType = organismMainType;
    }

    public Organism(String name, double weight, int maxOnCell, String icon, String organismMainType, String organismType,
                    Map<String, Integer> possibleFood, int speed, double energy, double maxFoodForSatiety) {
        this(name, weight, maxOnCell, icon, organismMainType, organismType);
        this.speed = speed;
        this.energy = energy;
        this.maxFoodForSatiety = maxFoodForSatiety;
        this.possibleFood = possibleFood;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setEnergy(double energy) {
        if (energy > 100)
            this.energy = 100;
        else
            this.energy = energy;
    }
}
