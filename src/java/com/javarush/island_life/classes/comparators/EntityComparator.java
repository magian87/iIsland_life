package com.javarush.island_life.classes.comparators;

import com.javarush.island_life.classes.entity.Entity;

import java.util.Comparator;

public class EntityComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        return o1.getEntityCharacteristics().getAnimalClass().toLowerCase()
                .compareTo(o2.getEntityCharacteristics().getAnimalClass().toLowerCase());
    }
}
