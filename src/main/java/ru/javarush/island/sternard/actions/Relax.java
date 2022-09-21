package ru.javarush.island.sternard.actions;

import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;

@SuppressWarnings("unused")// reflection api used this class and method
public class Relax {
    public void action(Animal animal, Cell cell) {

        double increaseAnimalEnergy = animal.increaseAnimalEnergy(animal);
        animal.setEnergy(increaseAnimalEnergy);

    }
}
