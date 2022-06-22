package com.javarush.island_life.classes.threads;

import com.javarush.island_life.classes.Island;

public class Grow implements Runnable {
    private Island island;

    public Grow(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        island.fillEntity("Plant");
    }
}
