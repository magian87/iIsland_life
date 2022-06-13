package com.javarush.island_life.classes.settints;

public class EntityCharacteristics implements Cloneable {
    private double weight;
    private int speed;
    private double saturation;
    private double currentSaturation;
    private String emoji;
    private String animalClass;

    public EntityCharacteristics(double weight, int speed, double saturation, double currentSaturation, String emoji, String animalClass) {
        this.weight = weight;
        this.speed = speed;
        this.saturation = saturation;
        this.currentSaturation = currentSaturation;
        this.emoji = emoji;
        this.animalClass = animalClass;
    }



    public EntityCharacteristics() {
    }

    public double getWeight() {
        return weight;
    }

    public int getSpeed() {
        return speed;
    }

    public double getSaturation() {
        return saturation;
    }

    public double getCurrentSaturation() {
        return currentSaturation;
    }

    public void setCurrentSaturation(double currentSaturation) {
        this.currentSaturation = currentSaturation;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getAnimalClass() {
        return animalClass;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    @Override
    public EntityCharacteristics clone()  {
        try {
            return (EntityCharacteristics) super.clone();
        } catch (CloneNotSupportedException e) {
            //e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "EntityCharacteristics{" +
                "weight=" + weight +
                ", speed=" + speed +
                ", saturation=" + saturation +
                ", currentSaturation=" + currentSaturation +
                ", emoji='" + emoji + '\'' +
                ", animalClass='" + animalClass + '\'' +
                '}';
    }
}
