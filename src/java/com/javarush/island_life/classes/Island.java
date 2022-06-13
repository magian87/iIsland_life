package com.javarush.island_life.classes;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island_life.classes.entity.Animal;
import com.javarush.island_life.classes.entity.Position;
import com.javarush.island_life.classes.settints.EntityIslandCharacteristics;
import com.javarush.island_life.classes.settints.EntityProducer;
import com.javarush.island_life.classes.settints.EntitySettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Island {

    private int height;
    private int width;

    public Map<Position, List<Animal>> landField3 = new HashMap<>();
    public Map<Integer, AnimalWithPosition> landField2 = new HashMap<>();

    public Map<String, EntityIslandCharacteristics> getEntityIslandCharacteristicsMap() {
        return entityIslandCharacteristicsMap;
    }

    private ObjectMapper objectMapper;
    private EntitySettings entitySettings;
    private EntityProducer entityProducer;
    private JsonNode jsonNode;

    private Map<String, EntityIslandCharacteristics> entityIslandCharacteristicsMap
            = new HashMap<>();

    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            entitySettings = objectMapper.readValue(
                    Files.newBufferedReader(Path.of("settings//entity.json")), EntitySettings.class);

            entityIslandCharacteristicsMap =
                    Arrays.stream(entitySettings.getEntityIslandCharacteristics())
                            .collect(Collectors.toMap(
                                    EntityIslandCharacteristics::getAnimalClass,
                                    s -> s));




            entityProducer = new EntityProducer(entitySettings.getEntityCharacteristics());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Island() {
        this.height = entitySettings.getIslandCharacteristics()[0].getHeight();
        this.width = entitySettings.getIslandCharacteristics()[0].getWidth();
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

    public int getAmountAnimalInCell(Position position, String animalClass) {
        int amountAnimal = 0;
        for (AnimalWithPosition value : landField2.values()) {
            if (value.position == position
                    && value.getAnimal().getEntityCharacteristics().getAnimalClass().equals(animalClass)) {
                amountAnimal++;
            }
            //System.out.println(position + " " + animalClass + " количество: " + amountAnimal);

        }
        return amountAnimal;
    }

    public void firstFillEntity() {
        /*for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //landField.put(Position.positionGetInstance(i,j), new LinkedList<>());
                //landField3.put(Position.positionGetInstance(i,j), new LinkedList<>());
            }
        }*/

        for (String classAnimal : entityProducer.receiveEntity()) {

            int maxAmountAnimal = entityIslandCharacteristicsMap.get(classAnimal).getMaxAmountOfThisAnimal();
            int maxAmountAnimalInCell = entityIslandCharacteristicsMap.get(classAnimal).getMaxAmountAnimalInCell()-1;

            for (int i = 0; i < maxAmountAnimal; i++) {
                Position position1;
                Random random = new Random();
                int x;
                int y;
                //List<Animal> animalList;
                //int cntTry=0;

                do {
                    x = random.nextInt(this.height);
                    y = random.nextInt(this.width);
                    position1 = Position.positionGetInstance(x, y);

                } while (getAmountAnimalInCell(position1, classAnimal) > maxAmountAnimalInCell);


                Animal animal = entityProducer.createEntity(classAnimal);
                //animal.setPosition(position1);
                AnimalWithPosition animalWithPosition = new AnimalWithPosition(animal, position1);

                this.landField2.put(animalWithPosition.hashCode(), animalWithPosition);
                animal.setX(x);
                animal.setY(y);

            }
        }
    }
        public void viewEntityByIsland2 () {

            landField3.clear();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    //landField.put(Position.positionGetInstance(i,j), new LinkedList<>());
                    landField3.put(Position.positionGetInstance(i, j), new LinkedList<>());
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
                max = Math.max(max, size);

            }

            System.out.println("---------------------------------");
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Position position1 = Position.positionGetInstance(i, j);
                    int cntAnimal = 0;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Animal animal : landField3.get(position1)) {
                        stringBuilder.append(animal.getEntityCharacteristics().getEmoji()).append(",");
                        cntAnimal++;
                    }
                    if (stringBuilder.isEmpty()) {
                        stringBuilder.append("[" + "  ".repeat(max) + "]");
                    } else {
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        stringBuilder.insert(0, "[");
                        stringBuilder.append("  ".repeat(max - cntAnimal));
                        stringBuilder.append("]");

                    }
                    System.out.print(stringBuilder);
                }
                System.out.println();
            }
        }


    }

