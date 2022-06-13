package com.javarush.island_life.classes;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island_life.classes.entity.Animal;
import com.javarush.island_life.classes.entity.Position;
import com.javarush.island_life.classes.settints.EntityProducer;
import com.javarush.island_life.classes.settints.EntitySettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Island {

    private int height;
    private int width;
    public Map<Position, List <Animal>> landField = new HashMap<>();
    public Map<Position, List <Animal>> landField3 = new HashMap<>();
    public Map<Integer, AnimalWithPosition> landField2 = new HashMap<>();

    private ObjectMapper objectMapper;
    private EntitySettings entitySettings;
    private EntityProducer entityProducer;
    private JsonNode jsonNode;
    private int cntAnimalInCell;

    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //Settings Entity (Animal, Plant)
            EntitySettings entitySettings = objectMapper.readValue(
                    Files.newBufferedReader(Path.of("settings//entity.json")), EntitySettings.class);
            //Settings island
      /*      EntitySettingsIsland entitySettingsIsland = objectMapper.readValue(
                    Files.newBufferedReader(Path.of("files//settingsIsland.json")), EntitySettingsIsland.class);*/

            jsonNode = objectMapper.readTree(Files.newBufferedReader(Path.of("settings//settingsIsland.json")));

            entityProducer = new EntityProducer(entitySettings.getEntityCharacteristics());





        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Island() {
        this.height = jsonNode.get("height").intValue();
        this.width  = jsonNode.get("width").intValue();
        this.cntAnimalInCell = jsonNode.get("cntAnimalInCell").intValue();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Island(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void firstFillEntity() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                landField.put(Position.positionGetInstance(i,j), new LinkedList<>());
                //landField3.put(Position.positionGetInstance(i,j), new LinkedList<>());
            }
        }

        for (String classAnimal : entityProducer.receiveEntity()) {
            int cntAnimal = jsonNode.get(classAnimal.toLowerCase()+"Count").intValue();
            for (int i = 0; i < cntAnimal ; i++) {
                Position position1;
                Random random = new Random();
                int x;
                int y;
                List<Animal> animalList;
                //int cntTry=0;

                do {
                    x = random.nextInt(this.height);
                    y = random.nextInt(this.width);
                    position1 = Position.positionGetInstance(x,y);
                    animalList = landField.get(position1);
                    //cntTry++;

                } while (animalList.size()==cntAnimalInCell );


                Animal animal = entityProducer.createEntity(classAnimal);
                //animal.setPosition(position1);
                AnimalWithPosition animalWithPosition = new AnimalWithPosition(animal,position1);

                this.landField2.put(animalWithPosition.hashCode(), animalWithPosition);
                //animal.setPosition(position1);

                animal.setX(x);
                animal.setY(y);
                //System.out.print (y+" ");
                animalList.add(animal);

                this.landField.put(position1, animalList);
            }
        }
    }

    public void viewEntityByIsland(){
        System.out.println("--------------------------------");
        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width ; j++) {
                Position position1 = Position.positionGetInstance(i,j);
                System.out.print(landField.get(position1)==null?"[  ]":"["+landField.get(position1)+"]");
            }
            System.out.println();
        }
    }

    public void viewEntityByIsland2(){

        landField3.clear();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //landField.put(Position.positionGetInstance(i,j), new LinkedList<>());
                landField3.put(Position.positionGetInstance(i,j), new LinkedList<>());
            }
        }

        //Преобразование  Map<Integer, AnimalWithPosition> to Map<Position, List <Animal>>
        for (AnimalWithPosition value : landField2.values()) {
            //AnimalWithPosition animal = value;
            List<Animal> animalList = landField3.get(value.getPosition());
            animalList.add(value.getAnimal());
        }

        int max = 0;
        for (Position position : landField3.keySet()) {
            int size = landField3.get(position).size();
            max= Math.max(max, size);

        }
        //System.out.println(max);

        /*for (Integer integer : landField2.keySet()) {
            AnimalWithPosition animalWithPosition = landField2.get(integer);
            List<Animal> animalList = landField3.get(animalWithPosition.position);
            animalList.add(animalWithPosition.animal);
        }*/

        System.out.println("---------------------------------");
        for (int i = 0; i < height ; i++) {
            for (int j = 0; j < width ; j++) {
                Position position1 = Position.positionGetInstance(i,j);
                int cntAnimal=0;
                StringBuilder stringBuilder = new StringBuilder();
                for (Animal animal : landField3.get(position1)) {
                    stringBuilder.append(animal.getEntityCharacteristics().getEmoji()).append(",");
                    cntAnimal++;
                }
                if (stringBuilder.isEmpty()) {
                    stringBuilder.append("["+"  ".repeat(max)+"]");
                } else {
                    stringBuilder.deleteCharAt(stringBuilder.length()-1);
                    stringBuilder.insert(0,"[");
                    stringBuilder.append("  ".repeat(max-cntAnimal));
                    stringBuilder.append("]");

                }
                System.out.print(stringBuilder);


                //System.out.print(landField3.get(position1)==null?"[  ]".repeat(8):"["+landField3.get(position1)+"]");

            }
            System.out.println();
        }
    }


}
