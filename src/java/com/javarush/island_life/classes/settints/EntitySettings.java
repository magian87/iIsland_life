package com.javarush.island_life.classes.settints;

public class EntitySettings {
    private EntityCharacteristics[] entityCharacteristics;

    private String color;

    private int animalCount;

    public int getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(int animalCount) {
        this.animalCount = animalCount;
    }

    public EntityCharacteristics[] getEntityCharacteristics() {
        return entityCharacteristics;
    }

    public void setEntityCharacteristics(EntityCharacteristics[] entityCharacteristics) {
        this.entityCharacteristics = entityCharacteristics;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
