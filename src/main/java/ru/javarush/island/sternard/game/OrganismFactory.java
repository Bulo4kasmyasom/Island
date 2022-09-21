package ru.javarush.island.sternard.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.javarush.island.sternard.controller.Controller;
import ru.javarush.island.sternard.organisms.parents.Animal;
import ru.javarush.island.sternard.organisms.parents.Organism;
import ru.javarush.island.sternard.organisms.parents.Plant;
import ru.javarush.island.sternard.settings.Settings;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrganismFactory {
    private OrganismFactory() {
    }

    public static Map<String, Organism> organismMapFromJson() {
        try (FileReader fileReader = new FileReader(Settings.get().getPathToOrganismsProperty())) {
            Type type = new TypeToken<Map<String, Organism>>() {
            }.getType();
            return new Gson().fromJson(fileReader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Organism createOrganism(String className) {
        String simpleClassName = Controller.getSimpleClassName(className);
        String classPath = Settings.get().getPathToFolderWithOrganisms() + simpleClassName;
        Organism organism = OrganismFactory.organismMapFromJson().get(simpleClassName);
        String name = organism.getName();
        double weight = organism.getWeight();
        int maxOnCell = organism.getMaxOnCell();
        String icon = organism.getIcon();
        String organismType = organism.getOrganismType();
        Map<String, Integer> possibleFood = organism.getPossibleFood();

        try {
            return getNewOrganism(classPath, organism, name, weight, maxOnCell, icon, organismType, possibleFood);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException
                 | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Organism getNewOrganism(String classPath, Organism organism, String name, double weight, int maxOnCell, String icon, String organismType, Map<String, Integer> possibleFood) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        double energy;
        int speed;
        double maxFoodForSatiety;
        String[] plants = Settings.get().getOrganismType().get("plant");
        List<String> listPlants = new ArrayList<>(List.of(plants));

        // TODO need refactoring, but i do not have any idea how...
        if (!listPlants.contains(organismType)) { // for animals
            speed = organism.getSpeed();
            energy = organism.getEnergy();
            maxFoodForSatiety = organism.getMaxFoodForSatiety();
            return (Animal) Class.forName(classPath)
                    .getConstructor(String.class, double.class, int.class, String.class, String.class, Map.class, int.class, double.class, double.class)
                    .newInstance(name, weight, maxOnCell, icon, organismType, possibleFood, speed, energy, maxFoodForSatiety);
        } else {
            return (Plant) Class.forName(classPath)// for organism type =  plants
                    .getConstructor(String.class, double.class, int.class, String.class, String.class)
                    .newInstance(name, weight, maxOnCell, icon, organismType);
        }
    }
}
