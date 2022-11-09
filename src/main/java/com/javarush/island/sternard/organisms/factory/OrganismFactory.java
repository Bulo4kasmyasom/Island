package com.javarush.island.sternard.organisms.factory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javarush.island.sternard.controller.Controller;
import com.javarush.island.sternard.exception.HandlerExceptions;
import com.javarush.island.sternard.organisms.parents.Organism;
import com.javarush.island.sternard.settings.Settings;
import com.javarush.island.sternard.utils.GameLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;

import static com.javarush.island.sternard.constant.lang.English.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrganismFactory {

    public static Map<String, Organism> organismMapFromJson() {
        String pathToOrganismsProperty = Settings.get().getPathToOrganismsProperty();
        String message = FILE_ERROR + pathToOrganismsProperty;

        try (InputStream inputStream = OrganismFactory.class.getResourceAsStream(pathToOrganismsProperty)) {
            if (inputStream != null) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                    Type type = new TypeToken<Map<String, Organism>>() {
                    }.getType();
                    return new Gson().fromJson(bufferedReader, type);
                }
            } else {
                GameLogger.getLog().error(message);
                throw new HandlerExceptions(message);
            }
        } catch (IOException e) {
            GameLogger.getLog().error(e.getMessage(), e);
            throw new HandlerExceptions(message, e.getStackTrace());
        }
    }

    public static Organism createOrganism(String className) {
        String simpleClassName = Controller.getSimpleClassName(className);
        Organism organism = OrganismFactory.organismMapFromJson().get(simpleClassName);
        String name = organism.getName();
        double weight = organism.getWeight();
        int maxOnCell = organism.getMaxOnCell();
        String icon = organism.getIcon();
        String organismType = organism.getOrganismType();
        String organismMainType = organism.getOrganismMainType();

        return getNewOrganism(simpleClassName, organism, name, weight, maxOnCell, icon, organismMainType, organismType);
    }

    private static Organism getNewOrganism(String simpleClassName, Organism organism, String name, Double weight,
                                           Integer maxOnCell, String icon, String organismMainType, String organismType) {

        if (organismMainType.equals("animal")) {
            int speed = organism.getSpeed();
            double energy = organism.getEnergy();
            double maxFoodForSatiety = organism.getMaxFoodForSatiety();
            Map<String, Integer> possibleFood = organism.getPossibleFood();

            Class<?>[] classes = {String.class, double.class, int.class, String.class, String.class, String.class,
                    Map.class, int.class, double.class, double.class};
            Object[] objects = {name, weight, maxOnCell, icon, organismMainType, organismType, possibleFood,
                    speed, energy, maxFoodForSatiety};
            return getNewInstance(simpleClassName, classes, objects);

        } else if (organismMainType.equals("plant")) {

            Class<?>[] classes = {String.class, double.class, int.class, String.class, String.class, String.class};
            Object[] objects = {name, weight, maxOnCell, icon, organismMainType, organismType};
            return getNewInstance(simpleClassName, classes, objects);
            /////////
        } else {
            GameLogger.getLog().error(INCORRECT_TYPES_OF_ORGANISMS_LOG);
            throw new HandlerExceptions(INCORRECT_TYPES_OF_ORGANISMS);
        }
    }

    private static Organism getNewInstance(String simpleClassName, Class<?>[] classes, Object[] objects) {
        try {
            return Settings.getClassesOrganisms().get(simpleClassName)
                    .getConstructor(classes)
                    .newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            GameLogger.getLog().error(e.getMessage(), e);
            throw new HandlerExceptions(ORGANISM_CREATION_ERROR, e.getStackTrace());
        }
    }

}