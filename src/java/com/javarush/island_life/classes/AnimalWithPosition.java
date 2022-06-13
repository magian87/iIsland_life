package com.javarush.island_life.classes;


import com.javarush.island_life.classes.entity.Animal;
import com.javarush.island_life.classes.entity.Position;

import java.util.List;
import java.util.Objects;

public class AnimalWithPosition {
    Animal animal;
    Position position;

    public void setPosition(Position position) {
        this.position = position;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "AnimalWithPosition{" +
                "animal=" + animal +
                ", position=" + position +
                '}';
    }

    public AnimalWithPosition(Animal animal, Position position) {
        this.animal = animal;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithPosition that = (AnimalWithPosition) o;
        return animal.equals(that.animal) && position.equals(that.position);
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(animal, position);
    }
}
