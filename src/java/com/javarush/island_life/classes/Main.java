package com.javarush.island_life.classes;


import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Main {

    public static void main(String[] args) {
        System.out.println();
        System.out.println(colorize("Добро пожаловать на жизнь на острове!!!", Attribute.YELLOW_TEXT(), Attribute.NONE()));

        Island island = new Island();
        island.firstFillEntity();

        island.viewEntityByIsland();

        //System.exit(1);

        int k = 1;
        while (k <= 25 ? true : false) {
            System.out.println(colorize("Шаг " + k, Attribute.GREEN_TEXT(), Attribute.NONE()));
            island.nextStep();
            //island.reUpdateIsland();
            island.removeNotAliveEntities();
            island.viewEntityByIsland();
            island.addNewAnimal();
            island.fillEntity("Plant");

            if (island.receiveAmountAnimal() == 0) {
                return;
            }
            k++;
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



