package com.javarush.island.sternard.settings;

import com.google.gson.Gson;
import com.javarush.island.sternard.actions.Eat;
import com.javarush.island.sternard.actions.Move;
import com.javarush.island.sternard.actions.Relax;
import com.javarush.island.sternard.actions.Reproduce;
import com.javarush.island.sternard.annotation.Check;
import com.javarush.island.sternard.enumeration.ConsoleColors;
import com.javarush.island.sternard.exception.HandlerExceptions;
import com.javarush.island.sternard.organisms.*;
import com.javarush.island.sternard.organisms.parents.Organism;
import com.javarush.island.sternard.result.Result;
import com.javarush.island.sternard.result.ResultCode;
import com.javarush.island.sternard.utils.CheckInputData;
import com.javarush.island.sternard.utils.GameLogger;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.javarush.island.sternard.constant.lang.English.*;

@SuppressWarnings({"UnusedDeclaration"})
@Getter
public class Settings {

    static {
        //This line can be removed if LOG4J2.Properties will move in to resources
        System.setProperty("log4j2.configurationFile", "src/main/resources/sternard/log4j2.properties");
    }

    private static final String GAME_SETTINGS_JSON = "/resources/sternard/GameSettings.json";
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

    @Check(minValue = 1, maxValue = 25, message = VALUE_MUST_BE + "1-25.")
    private int statisticColumns;

    @Check(minValue = 1, maxValue = 200, message = VALUE_MUST_BE + "1-200.")
    private int widthMap;

    @Check(minValue = 1, maxValue = 200, message = VALUE_MUST_BE + "1-200.")
    private int heightMap;

    @Check(minValue = 1, maxValue = 50, message = VALUE_MUST_BE + "1-50.")
    private int maxAnimalCountInCell;

    @Check(message = VALUE_MUST_BE + "0-100.")
    private int minEnergyToDie;

    @Check(message = VALUE_MUST_BE + "0-100.")
    private double reduceEnergyPercent;

    @Check(message = VALUE_MUST_BE + "0-100.")
    private double increaseEnergyPercent;

    @Check(minValue = 1, maxValue = 100000, message = VALUE_MUST_BE + "1-100000.")
    private int plantGrowPeriod;

    @Check(minValue = 1, message = VALUE_MUST_BE + "1-100.")
    private int energyForNewAnimals;

    @Check(minValue = 1, maxValue = 100000, message = VALUE_MUST_BE + "1-100000.")
    private int showStatisticsPeriod;

    @Check(minValue = 1, maxValue = 100000, message = VALUE_MUST_BE + "1-100000.")
    private int cellServicePeriod;

    @Check(minValue = 1, maxValue = 10000, message = VALUE_MUST_BE + "1-10000.")
    private int maxNumberOfDays;

    private boolean exceptionShowStackTrace;

    @Check(message = FIELD_IS_EMPTY)
    private Map<String, Integer> actions;

    @Check(message = FIELD_IS_EMPTY)
    private String[] directionsToMove;

    @Check(message = FIELD_IS_EMPTY)
    private Map<String, String[]> organismType;
    private static final Map<String, Class<?>> classesActions = new HashMap<>();
    private static final Map<String, Class<? extends Organism>> classesOrganisms = new HashMap<>();

    private Settings() {
        putOrganismActions();
        putOrganismClasses();
    }

    public void putOrganismActions() {
        classesActions.put("Eat", Eat.class);
        classesActions.put("Move", Move.class);
        classesActions.put("Relax", Relax.class);
        classesActions.put("Reproduce", Reproduce.class);
    }

    public void putOrganismClasses() {
        classesOrganisms.put("Bear", Bear.class);
        classesOrganisms.put("Boar", Boar.class);
        classesOrganisms.put("Buffalo", Buffalo.class);
        classesOrganisms.put("Caterpillar", Caterpillar.class);
        classesOrganisms.put("Chestnut", Chestnut.class);
        classesOrganisms.put("Deer", Deer.class);
        classesOrganisms.put("Duck", Duck.class);
        classesOrganisms.put("Eagle", Eagle.class);
        classesOrganisms.put("Fox", Fox.class);
        classesOrganisms.put("Goat", Goat.class);
        classesOrganisms.put("Horse", Horse.class);
        classesOrganisms.put("Lion", Lion.class);
        classesOrganisms.put("Mouse", Mouse.class);
        classesOrganisms.put("Mushroom", Mushroom.class);
        classesOrganisms.put("Palm", Palm.class);
        classesOrganisms.put("Panther", Panther.class);
        classesOrganisms.put("Rabbit", Rabbit.class);
        classesOrganisms.put("Sheep", Sheep.class);
        classesOrganisms.put("Snake", Snake.class);
        classesOrganisms.put("Spike", Spike.class);
        classesOrganisms.put("Sprig", Sprig.class);
        classesOrganisms.put("Tree", Tree.class);
        classesOrganisms.put("Wolf", Wolf.class);
    }

    public static Settings get() { // need to use a singleton...
        try (InputStream inputStream = Settings.class.getResourceAsStream(GAME_SETTINGS_JSON)) {
            if (inputStream != null) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                    Settings settingsFromJSON = new Gson().fromJson(bufferedReader, Settings.class);
                    Result validateSettingsResultCode = CheckInputData.check(settingsFromJSON);
                    if (validateSettingsResultCode.getResultCode() == ResultCode.OK)
                        return settingsFromJSON;
                    else {
                        String errorMessage = validateSettingsResultCode.getMessage();
                        GameLogger.getLog().error(errorMessage);
                        throw new HandlerExceptions(errorMessage);
                    }
                }
            } else {
                GameLogger.getLog().error(FILE_ERROR + GAME_SETTINGS_JSON);
                throw new HandlerExceptions(FILE_ERROR + GAME_SETTINGS_JSON);
            }
        } catch (IOException e) {
            GameLogger.getLog().error(e.getMessage(), e);
            throw new HandlerExceptions(FILE_ERROR + GAME_SETTINGS_JSON, e.getStackTrace());
        }

    }

    private String getColor(String color) {
        try {
            return ConsoleColors.valueOf(color.toUpperCase()).getAnsiColor();
        } catch (IllegalArgumentException e) {
            GameLogger.getLog().warn(e.getMessage());
            throw new HandlerExceptions(NO_SUCH_COLOR_IN_CONSOLE_COLORS);
        }
    }

    public String getStatisticTextColorDay() {
        return getColor(statisticTextColorDay);
    }

    public String getTextColorStatisticKey() {
        return getColor(textColorStatisticKey);
    }

    public String getTextColorStatisticValue() {
        return getColor(textColorStatisticValue);
    }

    public static Map<String, Class<?>> getClassesActions() {
        return classesActions;
    }

    public static Map<String, Class<? extends Organism>> getClassesOrganisms() {
        return classesOrganisms;
    }
}
