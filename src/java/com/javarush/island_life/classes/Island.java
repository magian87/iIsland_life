package com.javarush.island_life.classes;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island_life.classes.entity.Animal;
import com.javarush.island_life.classes.entity.Entity;
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


    //public Map<Integer, AnimalWithPosition> landField2 = new HashMap<>();

    public Map<Position, List<Entity>> landField = new HashMap<>();


    public Map<String, EntityIslandCharacteristics> getEntityIslandCharacteristicsMap() {
        return entityIslandCharacteristicsMap;
    }

    private ObjectMapper objectMapper;
    private EntitySettings entitySettings;
    private EntityProducer entityProducer;

   //private JsonNode jsonNode;

    private Map<String, EntityIslandCharacteristics> entityIslandCharacteristicsMap
            = new HashMap<>();

    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            //entitySettings = GetEntitySettings.receiveSettings();

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



    public long getAmountAnimalClassInCell(Position position, String animalClass) {
        return landField.get(position).stream()
                    .filter(s-> s.getEntityCharacteristics().getAnimalClass().equals(animalClass))
                    .count();


    }

    public void firstFillEntity() {
        for (int i = 0; i < this.height ; i++) {
            for (int j = 0; j < this.width ; j++) {
                List<Entity> list = new LinkedList<>();
                this.landField.put(Position.positionGetInstance(i,j), list);
            }
        }

        for (String classAnimal : entityProducer.receiveEntity()) {

            int maxAmountAnimal = entityIslandCharacteristicsMap.get(classAnimal).getMaxAmountOfThisAnimal();
            int maxAmountAnimalInCell = entityIslandCharacteristicsMap.get(classAnimal).getMaxAmountAnimalInCell()-1;

            for (int i = 0; i < maxAmountAnimal; i++) {
                Position position1;
                Random random = new Random();
                int x;
                int y;
                do {
                    x = random.nextInt(this.height);
                    y = random.nextInt(this.width);
                    position1 = Position.positionGetInstance(x, y);

                } while (getAmountAnimalClassInCell(position1, classAnimal) > maxAmountAnimalInCell);

                Entity entity = entityProducer.createEntity(classAnimal);

                entity.setPosition(position1);
                entity.setIsland(this);
                landField.get(position1).add(entity);
            }
        }
    }

    public Set<Position> receivePositions(){
        return landField.keySet();
    }

        public void viewEntityByIsland() {
            int max = 0;
            for (Position position : landField.keySet()) {
                int size = landField.get(position).size();
                max = Math.max(max, size);
            }

            System.out.println("---------------------------------");
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Position position1 = Position.positionGetInstance(i, j);
                    int cntAnimal = 0;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Entity entity : landField.get(position1)) {
                        stringBuilder.append(entity.getEntityCharacteristics().getEmoji()).append(",");
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
