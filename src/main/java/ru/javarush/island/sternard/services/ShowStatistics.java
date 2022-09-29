package ru.javarush.island.sternard.services;

import lombok.AllArgsConstructor;
import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.view.printStatisticsToConsole;
@AllArgsConstructor
public class ShowStatistics {
    private final Controller controller;
    public Runnable showStatisticsTask() {
        return () ->
        {
            controller.getDAY_NUMBER().getAndIncrement();
            new printStatisticsToConsole(controller).printStatistic();
        };
    }
}
