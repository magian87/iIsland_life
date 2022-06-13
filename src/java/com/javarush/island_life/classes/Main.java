package com.javarush.island_life.classes;


import com.diogonunes.jcolor.Attribute;
import com.javarush.island_life.classes.entity.Animal;
import com.javarush.island_life.classes.entity.Position;

import java.util.*;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Main {

    public static void main(String[] args) {
        System.out.println(colorize("Hello!!!", Attribute.YELLOW_TEXT(), Attribute.NONE()));

        Island island = new Island();
        //MovedAnimal movedAnimal = new MovedAnimal(island);

        island.firstFillEntity();

        island.viewEntityByIsland();

        List<Animal> animalList = new LinkedList<>();

        for (int i = 0; i < island.getHeight() ; i++) {
            for (int j = 0; j < island.getWidth() ; j++) {
                List<Animal> ll = island.landField.get(Position.positionGetInstance(i,j));
                if (ll.size()>0){
                    animalList.add(ll.get(0));
                }
            }

        }

        for (int i = 0; i < animalList.size(); i++) {
            animalList.get(i).step();
            System.out.println();
        }


/*        for (Position position : island.receivePositions()) {
            List<Animal> animalList = island.landField.get(position);
            //System.out.println(animalList);
            for (Animal animal : animalList) {
                animal.step();
            }
        }*/

        island.viewEntityByIsland();

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



