package ru.javarush.island.sternard.settings;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.javarush.island.sternard.actions.*;
import ru.javarush.island.sternard.exception.HandlerExceptions;
import ru.javarush.island.sternard.organisms.*;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.annotation.Check;
import ru.javarush.island.sternard.utils.CheckInputData;
import ru.javarush.island.sternard.utils.GameLogger;
import ru.javarush.island.sternard.utils.PathFinder;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.javarush.island.sternard.constant.lang.English.*;

@SuppressWarnings({"UnusedDeclaration"})
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Settings {
    private static final String GAME_SETTINGS_JSON = "src/main/resources/sternard/GameSettings.json";
    @Check(message = INVALID_FILE_PATH)
    public String pathToOrganismsProperty;

    @Check(message = NOT_NULL_AND_NOT_EMPTY)
    private String log4j2_properties;

    @Check(message = NOT_NULL_AND_NOT_EMPTY)
    private String textColorStatisticValue;

    @Check(message = NOT_NULL_AND_NOT_EMPTY)
    private String statisticTextColorDay;

    @Check(message = NOT_NULL_AND_NOT_EMPTY)
    private String textColorStatisticKey;

    @Check(minValue = 1,maxValue = 25, message = VALUE_MUST_BE + "1-25.")
    private int statisticColumns;

    @Check(minValue = 1,maxValue = 200, message = VALUE_MUST_BE + "1-200.")
    private int widthMap;

    @Check(minValue = 1,maxValue = 200, message = VALUE_MUST_BE + "1-200.")
    private int heightMap;

    @Check(minValue = 1,maxValue = 50, message = VALUE_MUST_BE + "1-50.")
    private int maxAnimalCountInCell;

    @Check(message = VALUE_MUST_BE + "0-100.")
    private int minEnergyToDie;

    @Check(message = VALUE_MUST_BE + "0-100.")
    private double reduceEnergyPercent;

    @Check(message = VALUE_MUST_BE + "0-100.")
    private double increaseEnergyPercent;

    @Check(minValue = 1,maxValue = 100000, message = VALUE_MUST_BE + "1-100000.")
    private int plantGrowTime;

    @Check(minValue = 1,maxValue = 100000, message = VALUE_MUST_BE + "1-100000.")
    private int showStatisticsPeriod;

    @Check(minValue = 1,maxValue = 100000, message = VALUE_MUST_BE + "1-100000.")
    private int lifeCyclePeriod;

    @Check(minValue = 1,maxValue = 10000, message = VALUE_MUST_BE + "1-10000.")
    private int maxNumberOfDays;

    private boolean exceptionShowStackTrace;

    @Check(message = FIELD_IS_EMPTY)
    private Map<String, Integer> actions;

    @Check(message = FIELD_IS_EMPTY)
    private String[] directionsToMove;

    @Check(message = FIELD_IS_EMPTY)
    private Map<String, String[]> organismType;

    private static final Map<String, Class<?>> classesActions = new HashMap<>() {{
        put("Eat", Eat.class);
        put("Move", Move.class);
        put("Relax", Relax.class);
        put("Reproduce", Reproduce.class);
    }};

    private static final Map<String, Class<? extends Organism>> classesOrganisms = new HashMap<>() {{
        put("Bear", Bear.class);
        put("Boar", Boar.class);
        put("Buffalo", Buffalo.class);
        put("Caterpillar", Caterpillar.class);
        put("Chestnut", Chestnut.class);
        put("Deer", Deer.class);
        put("Duck", Duck.class);
        put("Eagle", Eagle.class);
        put("Fox", Fox.class);
        put("Goat", Goat.class);
        put("Horse", Horse.class);
        put("Lion", Lion.class);
        put("Mouse", Mouse.class);
        put("Mushroom", Mushroom.class);
        put("Palm", Palm.class);
        put("Panther", Panther.class);
        put("Rabbit", Rabbit.class);
        put("Sheep", Sheep.class);
        put("Snake", Snake.class);
        put("Spike", Spike.class);
        put("Sprig", Sprig.class);
        put("Tree", Tree.class);
        put("Wolf", Wolf.class);
    }};

    public static Settings get() {
        String pathToSettingsFile = PathFinder.convertPathForAllOS(GAME_SETTINGS_JSON);
        try (FileReader fileReader = new FileReader(pathToSettingsFile)) {
            Settings settingsFromJSON = new Gson().fromJson(fileReader, Settings.class);
            String validateSettings = CheckInputData.check(settingsFromJSON);
            if (validateSettings.isEmpty())
                return settingsFromJSON;
            else {
                GameLogger.getLog().error(validateSettings);
                throw new HandlerExceptions(validateSettings);
            }
        } catch (IOException e) {
            GameLogger.getLog().error(e.getMessage(), e);
            throw new HandlerExceptions(FILE_ERROR + pathToSettingsFile, e.getStackTrace());
        }
    }

    public static Map<String, Class<?>> getClassesActions() {
        return classesActions;
    }
    public static Map<String, Class<? extends Organism>> getClassesOrganisms() {
        return classesOrganisms;
    }
    public String getPathToOrganismsProperty() {
        return PathFinder.convertPathForAllOS(pathToOrganismsProperty);
    }
}
