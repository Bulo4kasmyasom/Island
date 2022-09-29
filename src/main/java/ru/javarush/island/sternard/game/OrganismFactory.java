package ru.javarush.island.sternard.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.exception.HandlerExceptions;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.settings.Settings;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.javarush.island.sternard.constant.lang.English.FILE_ERROR;
import static ru.javarush.island.sternard.constant.lang.English.ORGANISM_CREATION_ERROR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrganismFactory {

    public static Map<String, Organism> organismMapFromJson() {
        String pathToOrganismsProperty = Settings.get().getPathToOrganismsProperty();
        try (FileReader fileReader = new FileReader(pathToOrganismsProperty)) {
            Type type = new TypeToken<Map<String, Organism>>() {
            }.getType();
            return new Gson().fromJson(fileReader, type);
        } catch (IOException e) {
            throw new HandlerExceptions(FILE_ERROR + pathToOrganismsProperty, e.getStackTrace());
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
        Map<String, Integer> possibleFood = organism.getPossibleFood();

        return getNewOrganism(simpleClassName, organism, name, weight, maxOnCell, icon, organismType, possibleFood);
    }

    private static Organism getNewOrganism(String simpleClassName, Organism organism, String name, double weight,
                                           int maxOnCell, String icon, String organismType,
                                           Map<String, Integer> possibleFood) {
        double energy;
        int speed;
        double maxFoodForSatiety;
        String[] plants = Settings.get().getOrganismType().get("plant");
        List<String> listPlants = new ArrayList<>(List.of(plants));

        try {
            if (!listPlants.contains(organismType)) { // for animals
                speed = organism.getSpeed();
                energy = organism.getEnergy();
                maxFoodForSatiety = organism.getMaxFoodForSatiety();

                //TODO need refactoring, but i do not have any idea how...
                return Settings.getClassesOrganisms().get(simpleClassName)
                        .getConstructor(String.class, double.class, int.class, String.class, String.class, Map.class,
                                int.class, double.class, double.class)
                        .newInstance(name, weight, maxOnCell, icon, organismType, possibleFood, speed, energy, maxFoodForSatiety);
            } else {
                return Settings.getClassesOrganisms().get(simpleClassName)// for organism type =  plants
                        .getConstructor(String.class, double.class, int.class, String.class, String.class)
                        .newInstance(name, weight, maxOnCell, icon, organismType);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new HandlerExceptions(ORGANISM_CREATION_ERROR, e.getStackTrace());
        }
    }
}
