package ru.javarush.island.sternard.services;

import lombok.AllArgsConstructor;
import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@AllArgsConstructor
public class CellService {
    private final Controller controller;
    public Runnable cellServiceStart(Cell cell) {
        return () -> {
            List<Animal> animals = new CopyOnWriteArrayList<>(cell.getAnimals());
            for (Animal animal : animals) {
                if (!controller.isDead(animal)) {
                    controller.animalChooseActionAndDoIt(animal, cell);
                } else {
                    cell.removeOrganism(animal);
                }
            }
        };
    }
}