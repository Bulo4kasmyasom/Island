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

public class printStatisticsToConsole {
    private final Controller controller;

    public printStatisticsToConsole(Controller controller) {
        this.controller = controller;
    }

    public void printStatistic() {
        Map<String, Organism> organismMapFromJson = OrganismFactory.organismMapFromJson();
        Map<String, Integer> statistics = new ConcurrentHashMap<>();
        List<Organism> allOrganisms = new CopyOnWriteArrayList<>();

        this.statisticCollect(statistics, allOrganisms);
        this.printStatisticChart(organismMapFromJson, statistics);
        this.printStatisticAllOrganismsCount(allOrganisms);
        System.out.println("\n");
    }

    public void printStatisticChart(Map<String, Organism> organismMapFromJson, Map<String, Integer> statistics) {
        String drawStatisticTextColor = Settings.get().getDrawStatisticTextColor();
        System.out.printf(" %s--== DAY %3d ==--\033[0m \n\n", drawStatisticTextColor, controller.getDAY_NUMBER().get());

        for (Map.Entry<String, Integer> key : statistics.entrySet()) {
            String drawStatisticColor = Settings.get().getDrawStatisticColor();
            int maxNumberOfDays = Settings.get().getMaxNumberOfDays();
            int maxAnimalCountInCell = Settings.get().getMaxAnimalCountInCell();
            int drawStatisticCoefficient = Settings.get().getDrawStatisticCoefficient();
            String drawStatisticSymbol = Settings.get().getDrawStatisticSymbol();
            int coefficient = (maxNumberOfDays / maxAnimalCountInCell) * drawStatisticCoefficient;
            String drawStatistic = drawStatisticSymbol.repeat(key.getValue() / coefficient);

            System.out.printf("%s %s %s\033[0m %d\n",
                    organismMapFromJson.get(key.getKey()).getIcon(), drawStatisticColor, drawStatistic, key.getValue());
        }
    }

    public void statisticCollect(Map<String, Integer> statistics, List<Organism> allOrganisms) {
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

    public void printStatisticAllOrganismsCount(List<Organism> allOrganisms) {
        //TODO not flexible, need refactoring
        int allOrganismCount = allOrganisms.size();
        long herbivoreCount = allOrganisms.stream().filter(o -> Objects.equals(o.getOrganismType(), "herbivore")).count();
        long carnivoreCount = allOrganisms.stream().filter(o -> Objects.equals(o.getOrganismType(), "carnivore")).count();
        long plants = allOrganismCount - (carnivoreCount + herbivoreCount);
        System.out.println();
        System.out.println("Организмов: " + allOrganismCount);
        System.out.println("Травоядных: " + herbivoreCount);
        System.out.println("Хищников: " + carnivoreCount);
        System.out.println("Растений: " + plants);
    }

}
