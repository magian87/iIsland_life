package com.javarush.islandlife.classes;


import com.diogonunes.jcolor.Attribute;
import com.javarush.islandlife.classes.threads.EndSimulation;
import com.javarush.islandlife.classes.threads.Grow;
import com.javarush.islandlife.classes.threads.LifeAnimals;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Main {

    public static void main(String[] args) {
        System.out.println();
        System.out.println(colorize("Добро пожаловать на жизнь на острове!!!", Attribute.YELLOW_TEXT(), Attribute.NONE()));

        Island island = new Island();
        island.firstFillEntity();

        //island.viewEntityByIsland();
        island.getStatistics();

        //System.exit(1);




        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);

        Runnable grow = new Grow(island);
        Runnable lifeAnimal = new LifeAnimals(island);
        Runnable endSimulation = new EndSimulation(island, ses);

        ses.scheduleAtFixedRate(grow, 100, 500, TimeUnit.MILLISECONDS);
        ses.scheduleAtFixedRate(lifeAnimal, 100, 500, TimeUnit.MILLISECONDS);
        ses.scheduleAtFixedRate(endSimulation, 100, 500, TimeUnit.MILLISECONDS);


 /*       int k = 1;
        while (k <= 100 ? true : false) {
            System.out.println(colorize("Шаг " + k, Attribute.GREEN_TEXT(), Attribute.NONE()));
            island.nextStep();

            island.fillEntity("Plant");

            island.getStatistics();

            island.removeNotAliveEntities();
            island.addNewAnimal();
            //System.out.println("!!!!!!!");
            //island.getStatistics();
            //island.viewEntityByIsland();

            if (island.receiveAmountAnimal() == 0) {
                return;
            }

            k++;
        }*/


    }
}








