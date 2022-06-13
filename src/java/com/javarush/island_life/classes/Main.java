package com.javarush.island_life.classes;


import com.diogonunes.jcolor.Attribute;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Main {

    public static void main(String[] args) {
        System.out.println(colorize("Hello!!!", Attribute.YELLOW_TEXT(), Attribute.NONE()));
        Island island = new Island();
        MovedAnimal movedAnimal = new MovedAnimal(island);

        island.firstFillEntity();

        island.viewEntityByIsland2();

        for (int i = 0; i < 10; i++) {
            movedAnimal.moveAnimal();
            island.viewEntityByIsland2();
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

 */


