package ru.javarush.island.sternard.controller;

import lombok.Getter;
import ru.javarush.island.sternard.exception.HandlerExceptions;
import ru.javarush.island.sternard.game.Cell;
import ru.javarush.island.sternard.game.OrganismFactory;
import ru.javarush.island.sternard.organisms.parents.Animal;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.settings.Settings;
import ru.javarush.island.sternard.utils.GameLogger;
import ru.javarush.island.sternard.utils.Randomizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javarush.island.sternard.constant.lang.English.*;

@Getter
public class Controller {

    public static final long START_TIMER = System.currentTimeMillis();
    private final ExecutorService cellRunExecutor;
    private final int height;
    private final int width;
    private final Cell[][] cells;
    private final AtomicInteger DAY_NUMBER = new AtomicInteger(0);
    private final Map<String, Integer> diedOrganisms = new HashMap<>();
    private final int corePoolSize = Runtime.getRuntime().availableProcessors();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(corePoolSize);

    public Controller(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        this.cellRunExecutor = Executors.newWorkStealingPool();
    }

    public void initGame(int maxOrganismsInCell) {
        GameLogger.getLog().info(INIT_GAME_START_WITH_ORGANISMS_IN_CELL, maxOrganismsInCell);
        int countOrganisms = 0;
        for (int y = 0; y < this.getHeight(); ++y) {
            for (int x = 0; x < this.getWidth(); ++x) {
                this.cells[y][x] = new Cell(x, y);
                for (int i = 0; i <= Randomizer.get(maxOrganismsInCell); ++i) {
                    Organism randomOrganism = this.getRandomOrganism();
                    int maxOrganismsInCellRandom = Randomizer.get(randomOrganism.getMaxOnCell());
                    for (int n = 0; n < maxOrganismsInCellRandom; n++) {
                        this.cells[y][x].addOrganism(randomOrganism);
                        countOrganisms++;
                    }
                }
            }
            if ((System.currentTimeMillis() - START_TIMER > 4000) && (y == this.getHeight() / 2)) {
                System.out.println(GAME_IS_INITIALIZE_WAIT);
            }
        }
        String initEndText = INIT_TIME_TEXT + ": " + (System.currentTimeMillis() - START_TIMER) + " ms";
        System.out.println(initEndText);
        GameLogger.getLog().info("initGame end. Created " + countOrganisms + " organisms. " + initEndText);
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
            Class<?> actionClassGetFromMap = Settings.getClassesActions().get(simpleClassNameAction);
            Object action = actionClassGetFromMap
                    .getConstructor()
                    .newInstance();

            // TODO need refactoring, but i do not have any idea how...
            if (simpleClassNameAction.equals("Move")) {
                Method method = action.getClass()
                        .getDeclaredMethod("action", Animal.class, Cell.class, Cell[][].class, int.class, int.class);
                method.invoke(action, animal, cell, this.getCells(), this.getHeight(), this.getWidth());
            } else {
                Method method = action.getClass().getDeclaredMethod("action", Animal.class, Cell.class);
                method.invoke(action, animal, cell);
            }
            ////////

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            GameLogger.getLog().error(e.getMessage(), e);
            throw new HandlerExceptions(ERROR_WITH_ACTION, e.getStackTrace());
        }
    }

    public static String getSimpleClassName(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static boolean isSameOrganisms(Organism organism, Organism o) {
        return Objects.equals(organism.getClass().getSimpleName(), o.getClass().getSimpleName());
    }

    public void stopSimulation() {
        getExecutorService().shutdown();
        long timeEndSimulation = System.currentTimeMillis() - START_TIMER;
        GameLogger.getLog().info(SIMULATION_END + timeEndSimulation + " ms");
    }

    public boolean isEndLifeCycle(int currentDay) {
        return currentDay >= Settings.get().getMaxNumberOfDays();
    }

    public boolean isDead(Animal animal) {
        double minWeight = animal.getWeight() - animal.getMaxFoodForSatiety();
        int minEnergyToDie = Settings.get().getMinEnergyToDie();
        if ((animal.getEnergy() <= minEnergyToDie) || (animal.getWeight() < minWeight)) {
            String iconAnimal = animal.getIcon();
            if (!diedOrganisms.containsKey(iconAnimal))
                diedOrganisms.put(iconAnimal, 1);
            else
                diedOrganisms.put(iconAnimal, diedOrganisms.get(iconAnimal) + 1);
            return true;
        }
        return false;
    }

}
