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
    private ObjectMapper objectMapper;
    private EntitySettings entitySettings;
    private EntityProducer entityProducer;


    //Так делать нельзя, постараюсь исправить, комментарий как буду исправлять в readme.md
    public Map<Position, List<Entity>> getLandField() {
        return landField;
    }

    //Основное поле, по которому идет отрисовка и заполнение
    private Map<Position, List<Entity>> landField = new HashMap<>();


    public Map<String, EntityIslandCharacteristics> getEntityIslandCharacteristicsMap() {
        return entityIslandCharacteristicsMap;
    }



    //private JsonNode jsonNode;

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
            throw new RuntimeException("Не найден файл настроек, программа не будет запущена!");
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
                .filter(s -> s.getEntityCharacteristics().getAnimalClass().equals(animalClass))
                .count();


    }

    public void firstFillEntity() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                List<Entity> list = new LinkedList<>();
                this.landField.put(Position.positionGetInstance(i, j), list);
            }
        }

        for (String classAnimal : entityProducer.receiveEntity()) {

            int maxAmountAnimal = entityIslandCharacteristicsMap.get(classAnimal).getMaxAmountOfThisAnimal();
            int maxAmountAnimalInCell = entityIslandCharacteristicsMap.get(classAnimal).getMaxAmountAnimalInCell() - 1;

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

    public Set<Position> receivePositions() {
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

    private List<Animal> receiveAnimal() {
        List<Animal> animalList = new LinkedList<>();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                for (Entity entity : landField.get(Position.positionGetInstance(i, j))) {
                    if (entity instanceof Animal) {
                        animalList.add(((Animal) entity));
                    }
                }
            }
        }
        return animalList;
    }

    public void nextStep() {
        List<Animal> animalList = receiveAnimal();
        //Ход животных, что бы несколько раз не сходить одним и тем же животным
        for (int i = 0; i < animalList.size(); i++) {
            animalList.get(i).step();
        }
    }


    /*//Заготовка что бы убрать обращение к island.landField из класса Animal
    public void reUpdateIsland() {
        //Получить список животных
        List<Animal> animalList = receiveAnimal();
        for (Animal animal : animalList) {
            List<Entity> animalList1 = landField.get(animal.getPosition());
            animalList1.add(animal);
        }


        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Iterator<Entity> entityIterator = landField.get(Position.positionGetInstance(i, j)).iterator();

                while (entityIterator.hasNext()) {
                    Entity nextEntity = entityIterator.next();
                    if (nextEntity instanceof Animal) {
                        Animal animal = ((Animal) nextEntity);
                        long cnt =
                                animalList.stream()
                                        .filter(s->s.equals(animal))
                                        .count();
                        if (cnt>0){
                            if (Position.positionGetInstance(i,j)!=animal.getPosition()) {
                                entityIterator.remove();
                            }
                        }
                    }

                }
            }
        }
        //Пройтись по острову, если животное входит в List-> Map<Position, List<Animal>> сверить позицию
        // (животного и остров-животное, если очищается, очистить из массива <Animal> по позиции


    }*/
}
