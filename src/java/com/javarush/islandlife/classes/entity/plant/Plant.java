package com.javarush.islandlife.classes.entity.plant;

import com.javarush.islandlife.classes.entity.Entity;
import com.javarush.islandlife.classes.settints.EntityCharacteristics;

public class Plant extends Entity {
    public Plant(EntityCharacteristics entityCharacteristics) {
        super(entityCharacteristics);
        this.setIsAlive(true);
    }

}