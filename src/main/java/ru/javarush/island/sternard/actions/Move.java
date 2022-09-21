package ru.javarush.island.sternard.actions;

import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;
import ru.javarush.island.sternard.settings.Settings;
import ru.javarush.island.sternard.utils.Randomizer;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration"}) // reflection api used this class and method
public class Move {

    public void action(Animal animal, Cell cell, Cell[][] cells, int height, int width) {

        int currColumn = cell.getColumn();
        int currRow = cell.getRow();
        List<Cell> nextCellList = new ArrayList<>();
        nextCellList.add(cells[currColumn][currRow]);

        int maxStepsSpeed = animal.getSpeed();
        for (int step = maxStepsSpeed; step > 0; --step) {

            String randomDirectionToMove = getRandomDirectionToMove();

            switch (randomDirectionToMove) {
                case "up" -> {
                    if (currColumn < height - 1) {
                        nextCellList.add(cells[currColumn + 1][currRow]);
                        currColumn++;
                    }
                }
                case "up_right" -> {
                    if ((currColumn < height - 1) && (currRow < width - 1)) {
                        nextCellList.add(cells[currColumn + 1][currRow + 1]);
                        currColumn++;
                        currRow++;
                    }
                }
                case "up_left" -> {
                    if ((currColumn < height - 1) && currRow > 0) {
                        nextCellList.add(cells[currColumn + 1][currRow - 1]);
                        currColumn++;
                        currRow--;
                    }
                }
                case "down" -> {
                    if (currColumn > 0) {
                        nextCellList.add(cells[currColumn - 1][currRow]);
                        currColumn--;
                    }
                }
                case "down_right" -> {
                    if (currColumn > 0 && (currRow < width - 1)) {
                        nextCellList.add(cells[currColumn - 1][currRow + 1]);
                        currColumn--;
                        currRow++;
                    }
                }
                case "down_left" -> {
                    if (currColumn > 0 && currRow > 0) {
                        nextCellList.add(cells[currColumn - 1][currRow - 1]);
                        currColumn--;
                        currRow--;
                    }
                }
                case "left" -> {
                    if (currRow > 0) {
                        nextCellList.add(cells[currColumn][currRow - 1]);
                        currRow--;
                    }
                }
                case "right" -> {
                    if (currRow < width - 1) {
                        nextCellList.add(cells[currColumn][currRow + 1]);
                        currRow++;
                    }
                }
            }

        }

        if (nextCellList.size() == 2) {
            nextCellList.get(nextCellList.size() - 1).addOrganism(animal);
            nextCellList.get(0).removeOrganism(animal);
        }

        double reduceAnimalEnergy = animal.reduceAnimalEnergy(animal);
        animal.setEnergy(reduceAnimalEnergy);
    }


    private String getRandomDirectionToMove() {
        String[] directionsToMove = Settings.get().getDirectionsToMove();
        return directionsToMove[Randomizer.get(directionsToMove.length)];
    }

}
