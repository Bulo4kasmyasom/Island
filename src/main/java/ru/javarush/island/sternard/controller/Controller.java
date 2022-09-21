package ru.javarush.island.sternard.controller;

import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.game.OrganismFactory;
import ru.javarush.island.sternard.organisms.parents.Animal;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.settings.Settings;
import ru.javarush.island.sternard.utils.Randomizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    private final ExecutorService cellRunExecutor;
    private final int height;
    private final int width;
    private final Cell[][] cells;
    private final AtomicInteger DAY_NUMBER = new AtomicInteger(0);
    private final int corePoolSize = Runtime.getRuntime().availableProcessors();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(corePoolSize);

    public Controller(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        this.cellRunExecutor = Executors.newWorkStealingPool();
    }

    public void initGame(int maxOrganismsInCell) {
        long startTimer = System.currentTimeMillis();
        for (int y = 0; y < this.getHeight(); ++y) {
            for (int x = 0; x < this.getWidth(); ++x) {
                this.cells[y][x] = new Cell(x, y);
                for (int i = 0; i <= Randomizer.get(maxOrganismsInCell); ++i) {
                    this.cells[y][x].addOrganism(this.getRandomOrganism());
                }
            }
            if ((System.currentTimeMillis() - startTimer > 4000) && y == this.getHeight() / 2) {
                System.out.println("Please wait, the game is initialized...");
            }
        }
        System.out.println("Init time: " + (System.currentTimeMillis() - startTimer) + " ms");
    }

    private Organism getRandomOrganism() {
        List<Map.Entry<String, Organism>> entries = OrganismFactory.organismMapFromJson().entrySet().stream().toList();
        Map.Entry<String, Organism> stringOrganismEntry = entries.get(Randomizer.get(entries.size()));
        return OrganismFactory.createOrganism(stringOrganismEntry.getKey());
    }

    public void startService(Runnable runnable, long initialDelay, long delay) {
        this.executorService.scheduleWithFixedDelay(runnable, initialDelay, delay, TimeUnit.MILLISECONDS);
    }

    public void animalChooseActionAndDoIt(Animal animal, Cell cell) {
        Map<String, Integer> actions = Settings.get().getActions();
        List<String> list = new ArrayList<>(actions.keySet());
        String keyAction = list.get(Randomizer.get(list.size()));
        String simpleClassNameAction = getSimpleClassName(keyAction);

        invokeAction(animal, cell, simpleClassNameAction);
    }

    private void invokeAction(Animal animal, Cell cell, String simpleClassNameAction) {
        try {
            Object o = createActionClass(simpleClassNameAction);
            String methodNameAction = Settings.get().getMethodNameAction();

            // TODO need refactoring, but i do not have any idea how...
            if (simpleClassNameAction.equals("Move")) {
                Method method = o.getClass()
                        .getDeclaredMethod(methodNameAction, Animal.class, Cell.class, Cell[][].class, int.class, int.class);
                method.invoke(o, animal, cell, this.getCells(), this.getHeight(), this.getWidth());
            } else {
                Method method = o.getClass().getDeclaredMethod(methodNameAction, Animal.class, Cell.class);
                method.invoke(o, animal, cell);
            }
            ////////

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSimpleClassName(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    private Object createActionClass(String actionClassName) throws InstantiationException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, ClassNotFoundException {

        return Class.forName(Settings.get().getPathToFolderWithActions() + actionClassName)
                .getConstructor()
                .newInstance();
    }

    public static boolean isSameOrganisms(Organism organism, Organism o) {
        return Objects.equals(organism.getClass().getSimpleName(), o.getClass().getSimpleName());
    }

    public void stopSimulation() {
        getExecutorService().shutdown();
    }

    public boolean isEndLifeCycle(int currentDay) {
        return currentDay >= Settings.get().getMaxNumberOfDays();
    }

    public boolean isDead(Animal animal) {
        double minWeight = animal.getWeight() - animal.getMaxFoodForSatiety();
        int minEnergyToDie = Settings.get().getMinEnergyToDie();
        return (animal.getEnergy() <= minEnergyToDie) || (animal.getWeight() < minWeight);
    }

    public AtomicInteger getDAY_NUMBER() {
        return DAY_NUMBER;
    }

    public ExecutorService getCellRunExecutor() {
        return cellRunExecutor;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

}
