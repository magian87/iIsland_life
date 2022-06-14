package com.javarush.island_life.classes.settints;

public class EntitySettings {
    private int test;
    private EntityCharacteristics[] entityCharacteristics;
    private EntityIslandCharacteristics[] entityIslandCharacteristics;

    public EatCharacteristics[] getEatCharacteristics() {
        return eatCharacteristics;
    }

    public void setEatCharacteristics(EatCharacteristics[] eatCharacteristics) {
        this.eatCharacteristics = eatCharacteristics;
    }

    private EatCharacteristics[] eatCharacteristics;

    public IslandCharacteristics[] getIslandCharacteristics() {
        return islandCharacteristics;
    }

    public void setIslandCharacteristics(IslandCharacteristics[] islandCharacteristics) {
        this.islandCharacteristics = islandCharacteristics;
    }

    private IslandCharacteristics[] islandCharacteristics;

    public void setTest(int test) {
        this.test = test;
    }

    public void setEntityIslandCharacteristics(EntityIslandCharacteristics[] entityIslandCharacteristics) {
        this.entityIslandCharacteristics = entityIslandCharacteristics;
    }

    public EntityIslandCharacteristics[] getEntityIslandCharacteristics() {
        return entityIslandCharacteristics;
    }

    public EntityCharacteristics[] getEntityCharacteristics() {
        return entityCharacteristics;
    }


    public void setEntityCharacteristics(EntityCharacteristics[] entityCharacteristics) {
        this.entityCharacteristics = entityCharacteristics;
    }


}
