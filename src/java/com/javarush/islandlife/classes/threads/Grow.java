package com.javarush.islandlife.classes.threads;

import com.javarush.islandlife.classes.Island;

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
