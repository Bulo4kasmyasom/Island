package ru.javarush.island.sternard.settings;

import com.google.gson.Gson;
import ru.javarush.island.sternard.utils.PathFinder;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings({"UnusedDeclaration"})
public class Settings {
    public static final String GAME_SETTINGS_JSON = "src/main/resources/sternard/GameSettings.json";

    private Settings() {
    }

    public String pathToOrganismsProperty;
    private String pathToFolderWithOrganisms;
    private String pathToFolderWithActions;
    private String methodNameAction;
    private String drawStatisticSymbol;
    private String drawStatisticTextColor;
    private String drawStatisticColor;
    private int widthMap;
    private int heightMap;
    private int maxAnimalCountInCell;
    private int minEnergyToDie;
    private double reduceEnergyPercent;
    private double increaseEnergyPercent;
    private int plantGrowTime;
    private int drawStatisticCoefficient;
    private int showStatisticsPeriod;
    private int maxNumberOfDays;
    private Map<String, Integer> actions;
    private String[] directionsToMove;
    private Map<String, String[]> organismType;

    public static Settings get() {
        String pathToSettingsFile = PathFinder.convertPathForAllOS(GAME_SETTINGS_JSON);
        try (FileReader fileReader = new FileReader(pathToSettingsFile)) {
            return new Gson().fromJson(fileReader, Settings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPathToOrganismsProperty() {
        return PathFinder.convertPathForAllOS(pathToOrganismsProperty);
    }

    public String getPathToFolderWithOrganisms() {
        return pathToFolderWithOrganisms;
    }

    public Map<String, Integer> getActions() {
        return actions;
    }

    public String getMethodNameAction() {
        return methodNameAction;
    }

    public Map<String, String[]> getOrganismType() {
        return organismType;
    }

    public int getDrawStatisticCoefficient() {
        return drawStatisticCoefficient;
    }

    public int getWidthMap() {
        return widthMap;
    }

    public String[] getDirectionsToMove() {
        return directionsToMove;
    }

    public int getHeightMap() {
        return heightMap;
    }

    public int getMinEnergyToDie() {
        return minEnergyToDie;
    }

    public String getPathToFolderWithActions() {
        return pathToFolderWithActions;
    }

    public int getMaxAnimalCountInCell() {
        return maxAnimalCountInCell;
    }

    public String getDrawStatisticTextColor() {
        return drawStatisticTextColor;
    }

    public String getDrawStatisticColor() {
        return drawStatisticColor;
    }

    public double getReduceEnergyPercent() {
        return reduceEnergyPercent;
    }

    public String getDrawStatisticSymbol() {
        return drawStatisticSymbol;
    }

    public double getIncreaseEnergyPercent() {
        return increaseEnergyPercent;
    }

    public int getPlantGrowTime() {
        return plantGrowTime;
    }

    public int getShowStatisticsPeriod() {
        return showStatisticsPeriod;
    }

    public int getMaxNumberOfDays() {
        return maxNumberOfDays;
    }
}
