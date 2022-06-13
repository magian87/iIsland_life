package com.javarush.island_life.classes.entity;

import com.javarush.island_life.classes.enums.DirectionMove;
import com.javarush.island_life.classes.settints.EntityCharacteristics;

public abstract class Animal extends Entity /*implements Cloneable*/{
    public DirectionMove getDirectionMove() {
        return directionMove;
    }

    public void setDirectionMove(DirectionMove directionMove) {
        this.directionMove = directionMove;
    }

    private DirectionMove directionMove;

    private EntityCharacteristics entityCharacteristics;

    public Animal(EntityCharacteristics entityCharacteristics) {
        this.directionMove = DirectionMove.RIGHT;
        this.entityCharacteristics = entityCharacteristics;
    }

    public EntityCharacteristics getEntityCharacteristics() {
        return entityCharacteristics;
    }

    public void eat() {

    }

    public void reproduction() {

    }

    public void choiceOfDirection() {

    }

    public Position step() {
        this.y = y+1;
        return Position.positionGetInstance(x, y);
    }

    @Override
    public String toString() {
        return entityCharacteristics.getEmoji();

/*        return "Animal{" +
                "weight=" + weight +
                ", speed=" + speed +
                ", appetite=" + appetite +
                ", name='" + name + '\'' +
                ", emoji='" + emoji + '\'' +
                '}';*/


    }

    /*public Animal clone() throws CloneNotSupportedException {
        Animal animal = (Animal) super.clone();
        //А надо ли, если у животных одного вида будут одинаковые параметры?
        animal.animalSettings = new AnimalSettings(animalSettings.getWeight(), animalSettings.getSpeed(),
                animalSettings.getAppetite(), animalSettings.getName(), animalSettings.getEmoji());
        return animal;

    }*/
}
