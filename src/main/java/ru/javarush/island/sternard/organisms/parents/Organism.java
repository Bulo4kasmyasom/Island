package ru.javarush.island.sternard.organisms.parents;

import java.util.Map;

@SuppressWarnings("unused") // private Organism() supressed
public class Organism {
    protected String name;
    protected double weight;
    protected int maxOnCell;
    protected String icon;
    public String organismType;
    protected int speed;
    protected double energy;
    protected double maxFoodForSatiety;
    protected Map<String, Integer> possibleFood;

    private Organism() {
    }

    public Organism(String name, double weight, int maxOnCell, String icon, String organismType) {
        this.name = name;
        this.weight = weight;
        this.maxOnCell = maxOnCell;
        this.icon = icon;
        this.organismType = organismType;
    }

    public Organism(String name, double weight, int maxOnCell, String icon, String organismType, Map<String, Integer> possibleFood, int speed, double energy, double maxFoodForSatiety) {
        this(name, weight, maxOnCell, icon, organismType);
        this.speed = speed;
        this.energy = energy;
        this.maxFoodForSatiety = maxFoodForSatiety;
        this.possibleFood = possibleFood;
    }

    public Map<String, Integer> getPossibleFood() {
        return possibleFood;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getMaxOnCell() {
        return maxOnCell;
    }

    public String getOrganismType() {
        return organismType;
    }

    public void setMaxOnCell(int maxOnCell) {
        this.maxOnCell = maxOnCell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public double getWeight() {
        return weight;
    }

    public double getMaxFoodForSatiety() {
        return this.maxFoodForSatiety;
    }

    public int getSpeed() {
        return this.speed;
    }

    public double getEnergy() {
        return this.energy;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setEnergy(double energy) {
        if (energy > 100)
            this.energy = 100;
        else
            this.energy = energy;
    }

    public void setMaxFoodForSatiety(double maxFoodForSatiety) {
        this.maxFoodForSatiety = maxFoodForSatiety;
    }

}
