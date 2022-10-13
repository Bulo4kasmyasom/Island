package com.javarush.island.sternard.services;

import lombok.AllArgsConstructor;
import com.javarush.island.sternard.controller.Controller;
import com.javarush.island.sternard.view.printToConsole;

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
