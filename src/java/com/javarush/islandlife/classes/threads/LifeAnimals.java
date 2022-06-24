package com.javarush.islandlife.classes.threads;

import com.javarush.islandlife.classes.Island;

public class LifeAnimals implements Runnable {
    private Island island;

    public LifeAnimals(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        island.nextStep();
        island.getStatistics();
        island.removeNotAliveEntities();
        island.addNewAnimal();
    }
}
