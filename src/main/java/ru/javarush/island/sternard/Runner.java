package ru.javarush.island.sternard;

import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.services.LifeCycle;
import ru.javarush.island.sternard.services.PlantGrow;
import ru.javarush.island.sternard.services.ShowStatistics;
import ru.javarush.island.sternard.settings.Settings;

public class Runner {

    public static void main(String[] args) {
        Controller controller = new Controller(Settings.get().getWidthMap(), Settings.get().getHeightMap());

        controller.initGame(Settings.get().getMaxAnimalCountInCell());
        controller.startService(new ShowStatistics(controller).showStatisticsTask(), 100L, Settings.get().getShowStatisticsPeriod());
        controller.startService(new LifeCycle(controller).lifeCycleService(), 100L, 100L);
        controller.startService(new PlantGrow(controller).createPlantGrowTask(), 100L, Settings.get().getPlantGrowTime());
    }
}
