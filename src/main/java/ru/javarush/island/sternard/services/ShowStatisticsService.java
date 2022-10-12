package ru.javarush.island.sternard.services;

import lombok.AllArgsConstructor;
import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.view.printToConsole;

@AllArgsConstructor
public class ShowStatisticsService {
    private final Controller controller;

    public Runnable showStatisticsServiceStart() {
        return () ->
        {
            controller.getDAY_NUMBER().getAndIncrement();
            new printToConsole(controller).printStatistic();
        };
    }
}
