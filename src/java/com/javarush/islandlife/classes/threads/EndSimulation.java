package com.javarush.islandlife.classes.threads;

import com.javarush.islandlife.classes.Island;

import java.util.concurrent.ScheduledExecutorService;

public class EndSimulation implements Runnable{
    private Island island;
    private ScheduledExecutorService ses;

    public EndSimulation(Island island, ScheduledExecutorService ses) {
        this.island = island;
        this.ses = ses;
    }

    @Override
    public void run() {
        if (island.receiveAmountAnimal() == 0){
            ses.shutdownNow();
        }


    }
}
