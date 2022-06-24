package com.javarush.islandlife.classes.entity;

import com.javarush.islandlife.classes.Island;
import com.javarush.islandlife.classes.settints.EntityCharacteristics;

public class Entity {
    private EntityCharacteristics entityCharacteristics;
    private Position position;
    private Island island;
    private boolean isAlive;
    private boolean isNewEntity;


    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean alive) {
        isAlive = alive;
    }


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

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isNewEntity() {
        return isNewEntity;
    }

    public void setNewEntity(boolean newEntity) {
        isNewEntity = newEntity;
    }

    public Entity(EntityCharacteristics entityCharacteristics) {
        this.entityCharacteristics = entityCharacteristics;
        this.isAlive = true;
        this.isNewEntity = false;
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

