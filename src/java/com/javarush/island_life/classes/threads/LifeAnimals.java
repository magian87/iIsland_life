package com.javarush.island_life.classes.threads;

import com.javarush.island_life.classes.Island;

import java.util.concurrent.locks.ReentrantLock;

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
