package ru.javarush.island.sternard.actions.interfaces;

import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;
@SuppressWarnings({"UnusedDeclaration"})
public interface Relaxing {
    void action(Animal animal, Cell cell);
}