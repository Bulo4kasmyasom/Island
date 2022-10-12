package ru.javarush.island.sternard.actions.interfaces;

import ru.javarush.island.sternard.map.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;

@SuppressWarnings({"UnusedDeclaration"})
public interface Moving {
    void action(Animal animal, Cell cell, Cell[][] cells, int height, int width);
}
