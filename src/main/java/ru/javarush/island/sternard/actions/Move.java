package ru.javarush.island.sternard.actions;

import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;
import ru.javarush.island.sternard.settings.Settings;
import ru.javarush.island.sternard.utils.Randomizer;

@SuppressWarnings({"UnusedDeclaration"}) // reflection api use this class and method
public class Move {

    public synchronized void action(Animal animal, Cell cell, Cell[][] cells, int height, int width) {
        int chanceToMoveRandom = Randomizer.get(100);
        int chanceToMove = Settings.get().getActions().get("move");

        if (chanceToMoveRandom < chanceToMove) {
            int currColumn = cell.getColumn();
            int currRow = cell.getRow();
            Cell oldCell = cells[currColumn][currRow];
            Cell nextCell = null;

            for (int step = 0; step <= animal.getSpeed(); step++) {
                nextCell = getNextStepCell(cells, height, width, currColumn, currRow, nextCell);
            }

            if (nextCell != null) {
                nextCell.addOrganism(animal);
                oldCell.removeOrganism(animal);
            }

            double reduceAnimalEnergy = animal.reduceAnimalEnergy(animal);
            animal.setEnergy(reduceAnimalEnergy);
        }
    }

    private Cell getNextStepCell(Cell[][] cells, int height, int width, int currColumn, int currRow, Cell nextCell) {
        switch (getRandomDirectionToMove()) {
            case "up" -> {
                if (currColumn < height - 1)
                    nextCell = cells[currColumn + 1][currRow];
            }
            case "up_right" -> {
                if ((currColumn < height - 1) && (currRow < width - 1))
                    nextCell = cells[currColumn + 1][currRow + 1];
            }
            case "up_left" -> {
                if ((currColumn < height - 1) && (currRow > 0))
                    nextCell = cells[currColumn + 1][currRow - 1];
            }
            case "down" -> {
                if (currColumn > 0)
                    nextCell = cells[currColumn - 1][currRow];
            }
            case "down_right" -> {
                if (currColumn > 0 && (currRow < width - 1))
                    nextCell = cells[currColumn - 1][currRow + 1];
            }
            case "down_left" -> {
                if (currColumn > 0 && currRow > 0)
                    nextCell = cells[currColumn - 1][currRow - 1];
            }
            case "left" -> {
                if (currRow > 0)
                    nextCell = cells[currColumn][currRow - 1];
            }
            case "right" -> {
                if (currRow < width - 1)
                    nextCell = cells[currColumn][currRow + 1];
            }
        }
        return nextCell;
    }

    private String getRandomDirectionToMove() {
        String[] directionsToMove = Settings.get().getDirectionsToMove();
        return directionsToMove[Randomizer.get(directionsToMove.length)];
    }

}
