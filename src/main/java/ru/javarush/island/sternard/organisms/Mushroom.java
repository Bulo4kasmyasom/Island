package ru.javarush.island.sternard.organisms;

import ru.javarush.island.sternard.organisms.parents.Plant;

public class Mushroom extends Plant {
    public Mushroom(String name, double weight, int maxOnCell, String icon,  String organismMainType,String organismType) {
        super(name, weight, maxOnCell, icon, organismMainType,organismType);
    }
}
