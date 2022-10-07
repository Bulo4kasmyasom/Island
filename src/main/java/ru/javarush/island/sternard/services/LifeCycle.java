package ru.javarush.island.sternard.services;

import lombok.AllArgsConstructor;
import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.game.Cell;
@AllArgsConstructor
public class LifeCycle {
    private final Controller controller;
    public Runnable lifeCycleService() {
        return () -> {
            if (controller.isEndLifeCycle(controller.getDAY_NUMBER().get())) {
                controller.stopSimulation();
            }
            for (int height = 0; height < controller.getHeight(); ++height) {
                for (int width = 0; width < controller.getWidth(); ++width) {
                    Cell cell = controller.getCells()[height][width];
                    Runnable task = new CellService(controller).cellServiceStart(cell);
                    controller.getCellRunExecutor().submit(task);
                }
            }
        };
    }


}
