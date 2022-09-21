package ru.javarush.island.sternard.services;

import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.view.printStatisticsToConsole;

public class ShowStatistics {
    private final Controller controller;

    public ShowStatistics(Controller controller) {
        this.controller = controller;
    }

    public Runnable showStatisticsTask() {
        return () ->
        {
            controller.getDAY_NUMBER().getAndIncrement();
            new printStatisticsToConsole(controller).printStatistic();
        };
    }
}
