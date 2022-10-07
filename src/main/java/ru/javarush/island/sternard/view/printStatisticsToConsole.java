package ru.javarush.island.sternard.view;

import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.game.OrganismFactory;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.settings.Settings;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.javarush.island.sternard.constant.lang.English.*;

public class printStatisticsToConsole {
    private final Controller controller;
    private final String textColor = Settings.get().getTextColorStatisticKey();
    private final String drawStatisticTextColor = Settings.get().getStatisticTextColorDay();
    private final String textColorStatisticValue = Settings.get().getTextColorStatisticValue();
    private final int statisticColumns = Settings.get().getStatisticColumns();

    public printStatisticsToConsole(Controller controller) {
        this.controller = controller;
    }

    public void printStatistic() {
        Map<String, Organism> organismMapFromJson = OrganismFactory.organismMapFromJson();
        Map<String, Integer> statistics = new ConcurrentHashMap<>();
        List<Organism> allOrganisms = new CopyOnWriteArrayList<>();
        this.statisticCollect(statistics, allOrganisms);
        System.out.printf(" %s" + DAY_NUMBER + "\033[0m \n", drawStatisticTextColor, controller.getDAY_NUMBER().get());
        this.printStatisticTable(organismMapFromJson, statistics);
        this.printStatisticAllOrganismsCount(allOrganisms);
        System.out.println("\n");
    }

    private void printStatisticTable(Map<String, Organism> organismMapFromJson, Map<String, Integer> statistics) {
        int table = 0;
        for (Map.Entry<String, Integer> key : statistics.entrySet()) {
            if (table % statisticColumns == 0)
                System.out.println();

            System.out.printf("%s %s%-10d\033[0m \t", organismMapFromJson.get(key.getKey()).getIcon(),
                    textColorStatisticValue, key.getValue());
            table++;
        }
    }

    private void statisticCollect(Map<String, Integer> statistics, List<Organism> allOrganisms) {
        for (int y = 0; y < controller.getHeight(); ++y) {
            for (int x = 0; x < controller.getWidth(); ++x) {
                Cell cell = controller.getCells()[y][x];
                allOrganisms.addAll(cell.getOrganisms());
            }
        }
        for (Organism organism : allOrganisms) {
            String organismSimpleName = organism.getClass().getSimpleName();
            if (!statistics.containsKey(organismSimpleName))
                statistics.put(organismSimpleName, 1);
            else
                statistics.put(organismSimpleName, statistics.get(organismSimpleName) + 1);
        }
    }

    private void printStatisticAllOrganismsCount(List<Organism> allOrganisms) {
        long allOrganismCount = allOrganisms.size();
        long herbivoreCount = allOrganisms.stream()
                .filter(o -> Objects.equals(o.getOrganismType(), "herbivore"))
                .count();
        long carnivoreCount = allOrganisms.stream()
                .filter(o -> Objects.equals(o.getOrganismType(), "carnivore"))
                .count();
        long animalsCount = allOrganisms.stream()
                .filter(o -> Objects.equals(o.getOrganismMainType(), "animal"))
                .count();
        long plantsCount = allOrganisms.stream()
                .filter(o -> Objects.equals(o.getOrganismMainType(), "plant"))
                .count();
        int animalsDiedNumber = controller.getDiedOrganisms()
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
        printToConsole(allOrganismCount, herbivoreCount, carnivoreCount, animalsCount, plantsCount, animalsDiedNumber);
    }

    private void printToConsole(long allOrganismCount, long herbivoreCount, long carnivoreCount, long animalsCount, long plantsCount, int animalsDiedNumber) {
        System.out.println();
        printToConsoleFormat(ORGANISMS, allOrganismCount);
        printToConsoleFormat(HERBIVORES, herbivoreCount);
        printToConsoleFormat(CARNIVORES, carnivoreCount);
        printToConsoleFormat(ANIMALS, animalsCount);
        printToConsoleFormat(PLANTS, plantsCount);
        printToConsoleFormat(DIED_ORGANISMS, animalsDiedNumber);
        if (animalsDiedNumber > 0) {
            System.out.print(textColor + DIED_ORGANISMS + ": \033[0m");
            controller.getDiedOrganisms().entrySet().stream()
                    .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())) // sort by map value
                    .limit(7)
                    .forEach(k -> System.out.print(k.getKey() + "(" + textColorStatisticValue + k.getValue() + "\033[0m) "));
        }
    }

    private void printToConsoleFormat(String text, long count) {
        System.out.println(textColor + text + ": \033[0m" + textColorStatisticValue + count + "\033[0m");
    }

}
