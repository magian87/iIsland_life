package com.javarush.island_life.classes.settints;

public class EntityIslandCharacteristics {
    private String animalClass;
    private int maxAmountAnimalInCell;
    private  int maxAmountOfThisAnimal;

    @Override
    public String toString() {
        return "EntityIslandCharacteristics{" +
                "animalClass='" + animalClass + '\'' +
                ", maxAmountAnimalInCell=" + maxAmountAnimalInCell +
                ", maxAmountOfThisAnimal=" + maxAmountOfThisAnimal +
                '}';
    }

    public String getAnimalClass() {
        return animalClass;
    }

    public int getMaxAmountAnimalInCell() {
        return maxAmountAnimalInCell;
    }

    public int getMaxAmountOfThisAnimal() {
        return maxAmountOfThisAnimal;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    public void setMaxAmountAnimalInCell(int maxAmountAnimalInCell) {
        this.maxAmountAnimalInCell = maxAmountAnimalInCell;
    }

    public void setMaxAmountOfThisAnimal(int maxAmountOfThisAnimal) {
        this.maxAmountOfThisAnimal = maxAmountOfThisAnimal;
    }

    public EntityIslandCharacteristics() {
    }
}
