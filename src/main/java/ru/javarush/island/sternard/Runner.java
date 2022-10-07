package ru.javarush.island.sternard;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.services.LifeCycle;
import ru.javarush.island.sternard.services.PlantGrow;
import ru.javarush.island.sternard.services.ShowStatistics;
import ru.javarush.island.sternard.settings.Settings;
import ru.javarush.island.sternard.utils.GameLogger;

import static ru.javarush.island.sternard.constant.lang.English.THE_GAME_WAS_EMERGENCY_STOPPED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Runner {

    public static void main(String[] args) {

        //This line can be removed if move LOG4J2.Properties in to resources
        System.setProperty("log4j.configurationFile", Settings.get().getLog4j2_properties());

        // is it necessary to take out to variables ?...
        int plantGrowTime = Settings.get().getPlantGrowTime();
        int showStatisticsPeriod = Settings.get().getShowStatisticsPeriod();
        int lifeCyclePeriod = Settings.get().getLifeCyclePeriod();
        int maxAnimalCountInCell = Settings.get().getMaxAnimalCountInCell();
        int widthMap = Settings.get().getWidthMap();
        int heightMap = Settings.get().getHeightMap();

        Controller controller = new Controller(widthMap, heightMap);
        controller.initGame(maxAnimalCountInCell);

        Runnable plantGrowTask = new PlantGrow(controller).createPlantGrowTask();
        Runnable runnable = new ShowStatistics(controller).showStatisticsTask();
        Runnable runnable1 = new LifeCycle(controller).lifeCycleService();

        controller.startService(plantGrowTask, 20L, plantGrowTime);
        controller.startService(runnable, 30L, showStatisticsPeriod);
        controller.startService(runnable1, 40L, lifeCyclePeriod);

        // if game emergency stopped
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            boolean endLifeCycle = controller.isEndLifeCycle(controller.getDAY_NUMBER().get());
            if (!endLifeCycle)
                GameLogger.getLog().warn(THE_GAME_WAS_EMERGENCY_STOPPED);
        }));
    }
}
