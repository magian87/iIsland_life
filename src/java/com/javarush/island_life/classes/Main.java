package com.javarush.island_life.classes;


import com.diogonunes.jcolor.Attribute;
import com.javarush.island_life.classes.entity.Animal;
import com.javarush.island_life.classes.entity.Entity;
import com.javarush.island_life.classes.entity.Position;

import java.util.LinkedList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Main {

    public static void main(String[] args) {
        System.out.println();
        System.out.println(colorize("Hello!!!", Attribute.YELLOW_TEXT(), Attribute.NONE()));

        Island island = new Island();
        island.firstFillEntity();

        island.viewEntityByIsland();

        for (int k = 0; k < 5; k++) {
            island.nextStep();
            //island.reUpdateIsland();
            island.viewEntityByIsland();
            }


        }
}



/*Path path = Path.of("files//animalConfig1.properties");

        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader(path.toAbsolutePath().toString())) {
            properties.load(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

"Horse": 10,
    "Deer": 15,
    "Rabbit": 60,
    "Mouse": 80,
    "Goat": 60,
    "Sheep": 70,
    "Boar":  15,
    "Buffalo": 10,
    "Duck": 40


    /*
         //jsonNode = objectMapper.readTree(Files.newBufferedReader(Path.of("settings//settingsIsland.json")));
            //this.height = jsonNode.get("height").intValue();
            //this.width = jsonNode.get("width").intValue();
    * */



