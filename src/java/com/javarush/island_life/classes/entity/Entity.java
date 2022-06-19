package com.javarush.island_life.classes.entity;

import com.javarush.island_life.classes.Island;
import com.javarush.island_life.classes.settints.EntityCharacteristics;

public class Entity {
    private EntityCharacteristics entityCharacteristics;
    private Position position;
    private Island island;

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean alive) {
        isAlive = alive;
    }

    private boolean isAlive ;


    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Entity(EntityCharacteristics entityCharacteristics) {
        this.entityCharacteristics = entityCharacteristics;
    }

    public EntityCharacteristics getEntityCharacteristics() {
        return entityCharacteristics;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityCharacteristics=" + entityCharacteristics +
                '}';
    }
}

