package ru.javarush.island.sternard.actions;

import ru.javarush.island.sternard.actions.interfaces.Relaxing;
import ru.javarush.island.sternard.map.Cell;
import ru.javarush.island.sternard.organisms.parents.Animal;
import ru.javarush.island.sternard.settings.Settings;
import ru.javarush.island.sternard.utils.Randomizer;

@SuppressWarnings("unused")// reflection api use this class and method
public class Relax implements Relaxing {
    public void action(Animal animal, Cell cell) {

        int relaxChance = Settings.get().getActions().get("relax");
        int randomChanceToRelax = Randomizer.get(100);

        if (relaxChance > randomChanceToRelax) {
            animal.increaseAnimalEnergy(animal);
        }
    }
}
