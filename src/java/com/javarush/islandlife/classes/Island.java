package com.javarush.islandlife.classes;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.islandlife.classes.comparators.EntityComparator;
import com.javarush.islandlife.classes.entity.Animal;
import com.javarush.islandlife.classes.entity.Entity;
import com.javarush.islandlife.classes.entity.Position;
import com.javarush.islandlife.classes.settints.EatCharacteristics;
import com.javarush.islandlife.classes.settints.EntityIslandCharacteristics;
import com.javarush.islandlife.classes.settints.EntityProducer;
import com.javarush.islandlife.classes.settints.EntitySettings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.javarush.islandlife.classes.ConstantIsland.STATISTIC_INFO;

public class Island {

    private final int height;
    private final int width;
    private ObjectMapper objectMapper;
    private final EntitySettings entitySettings;

    public EntityProducer getEntityProducer() {
        return entityProducer;
    }

    private final EntityProducer entityProducer;

    private final Map<String, Map<String, Integer>> probabilityKillMap = new HashMap<>();

    private List<Entity> natureIslandList = new CopyOnWriteArrayList<>();

    public Map<String, EntityIslandCharacteristics> getEntityIslandCharacteristicsMap() {
        return entityIslandCharacteristicsMap;
    }

    private final Map<String, EntityIslandCharacteristics> entityIslandCharacteristicsMap;

    public List<Entity> getNatureIslandList() {
        return natureIslandList;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            entitySettings = objectMapper.readValue(
                    Files.newBufferedReader(Path.of("settings//entity.json")), EntitySettings.class);


            for (EatCharacteristics eatCharacteristic : entitySettings.getEatCharacteristics()) {
                Map<String, Integer> eatMap = new HashMap<>();
                eatMap.put("Wolf", eatCharacteristic.getWolf());
                eatMap.put("Snake", eatCharacteristic.getSnake());
                eatMap.put("Fox", eatCharacteristic.getFox());
                eatMap.put("Bear", eatCharacteristic.getBear());
                eatMap.put("Eagle", eatCharacteristic.getEagle());
                eatMap.put("Horse", eatCharacteristic.getHorse());
                eatMap.put("Deer", eatCharacteristic.getDeer());
                eatMap.put("Rabbit", eatCharacteristic.getRabbit());
                eatMap.put("Mouse", eatCharacteristic.getMouse());
                eatMap.put("Goat", eatCharacteristic.getGoat());
                eatMap.put("Sheep", eatCharacteristic.getSheep());
                eatMap.put("Boar", eatCharacteristic.getBoar());
                eatMap.put("Buffalo", eatCharacteristic.getBuffalo());
                eatMap.put("Duck", eatCharacteristic.getDuck());
                eatMap.put("Caterpillar", eatCharacteristic.getCaterpillar());
                eatMap.put("Plant", eatCharacteristic.getPlant());

                probabilityKillMap.put(eatCharacteristic.getPredator(), eatMap);
            }
            entityIslandCharacteristicsMap =
                    Arrays.stream(entitySettings.getEntityIslandCharacteristics())
                            .collect(Collectors.toMap(
                                    EntityIslandCharacteristics::getAnimalClass,
                                    s -> s));

            entityProducer = new EntityProducer(entitySettings.getEntityCharacteristics());


        } catch (FileNotFoundException e) {
            throw new RuntimeException("Не найден файл настроек, программа не будет запущена!");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Island() {
        this.height = entitySettings.getIslandCharacteristics()[0].getHeight();
        this.width = entitySettings.getIslandCharacteristics()[0].getWidth();
    }


    public Map<String, Map<String, Integer>> receiveProbabilityKillMap() {
        return probabilityKillMap;
    }


    public long receiveAmountAnimalClassInCell(Position position, String animalClass) {
        return natureIslandList.stream()
                .filter(s -> s.getPosition().equals(position))
                .filter(s -> s.getEntityCharacteristics().getAnimalClass().equals(animalClass))
                .count();
    }

    public void fillEntity(String entityClass) {
        int maxAmountAnimal = ThreadLocalRandom.current().nextInt(entityIslandCharacteristicsMap.get(entityClass).getMaxAmountOfThisAnimal() + 1);
        int maxAmountAnimalInCell = entityIslandCharacteristicsMap.get(entityClass).getMaxAmountAnimalInCell() - 1;


        for (int i = 0; i < maxAmountAnimal; i++) {
            Position position1;
            int x;
            int y;
            int cntTry = 0;
            do {
                x = ThreadLocalRandom.current().nextInt(this.height);
                y = ThreadLocalRandom.current().nextInt(this.width);
                position1 = Position.positionGetInstance(x, y);
                cntTry++;

            } while (receiveAmountAnimalClassInCell(position1, entityClass) > maxAmountAnimalInCell /*&& cntTry<20*/);

            Entity entity = entityProducer.createEntity(entityClass);

            entity.setPosition(position1);
            entity.setIsland(this);
            natureIslandList.add(entity);

        }

    }

    public void firstFillEntity() {
        for (String classAnimal : entityProducer.receiveEntity()) {
            fillEntity(classAnimal);
        }
    }

    private int receiveMaxAmountEntitiesInAllPositions() {
        double result = natureIslandList.stream()
                .collect(Collectors.groupingBy(
                        Entity::getPosition, Collectors.counting()))
                .values()
                .stream()
                .max(Comparator.comparing(Long::valueOf))
                .orElse(0L);
        return ((int) result);
    }

    private String receiveEmojisByPosition(Position position) {
        return natureIslandList.stream()
                .filter(s -> s.getPosition().equals(position))
                .sorted(new EntityComparator())
                .map(s -> s.getEntityCharacteristics().getEmoji())
                .collect(Collectors.joining(""));
    }

    public List<Entity> receiveEntitiesByPosition(Position position) {
        return natureIslandList.stream()
                .filter(s -> s.getPosition().equals(position))
                .collect(Collectors.toList());
    }

    public void viewEntityByIsland() {
        int maxLength = receiveMaxAmountEntitiesInAllPositions();

        StringBuilder stringBuilder = new StringBuilder("   ");
        for (int i = 0; i < width; i++) {
            stringBuilder.append("[" + i + "   ".repeat(maxLength > 0 ? maxLength / 2 : 0) + "]");
        }
        System.out.println(stringBuilder);

        for (int i = 0; i < height; i++) {
            System.out.format("%3d", i);
            for (int j = 0; j < width; j++) {
                Position position = Position.positionGetInstance(i, j);
                String curEmojis = receiveEmojisByPosition(position);
                // делю на два, т.к. длина одного эмоджи 2 знака, можно было конечно еще один стрим сделать...
                String ss = "[%s" + "  ".repeat(maxLength - curEmojis.length() / 2) + "]";
                System.out.printf(ss, curEmojis);
            }
            System.out.println();
        }
    }

    public int receiveAmountAnimal() {
        long result;
        result = natureIslandList.stream()
                .filter(s -> !s.getEntityCharacteristics().getAnimalClass().equals("Plant"))
                .count();
        return ((int) result);
    }

    public void nextStep() {
        for (Entity entity : natureIslandList) {
            if (entity instanceof Animal animal) {
                animal.step();
            }
        }
    }

    public void removeNotAliveEntities() {
            natureIslandList.removeIf(entity -> !entity.isAlive());
    }

    public void addNewAnimal() {
        List<Animal> parentAnimal =
                natureIslandList.stream()
                        .filter(s -> s instanceof Animal)
                        .map(s -> ((Animal) s))
                        .filter(s -> s.getAmountSpringOff() > 0)
                        .collect(Collectors.toList());

        for (Animal animal : parentAnimal) {
            for (int i = 0; i < animal.getAmountSpringOff(); i++) {
                Entity entity = entityProducer.createEntity(animal.getEntityCharacteristics().getAnimalClass());
                entity.setPosition(animal.getPosition());
                entity.setIsland(this);
                natureIslandList.add(entity);

                /*System.out.println(colorize("Родилось новое животно: " +
                        animal.getEntityCharacteristics().getAnimalClass(), Attribute.BRIGHT_MAGENTA_TEXT(), Attribute.NONE()));
*/
            }

        }

    }

    public Entity receivePairForReproduction(Entity entity) {
        return natureIslandList.stream()
                .filter(s -> s.getPosition().equals(entity.getPosition()))
                .filter(s -> s.getEntityCharacteristics().getAnimalClass()
                        .equals(entity.getEntityCharacteristics().getAnimalClass()))
                .filter(s -> s.getEntityCharacteristics().getCurrentSaturation() != 0)
                .filter(s -> ((Animal) s).getAmountStepWithoutSaturation() == 0)
                .filter(s -> !s.equals(entity))
                .findFirst()
                .orElse(null);
    }

    public void getStatistics() {

            long cntPlant =
                    natureIslandList.stream()
                            .filter(s -> s.getEntityCharacteristics().getAnimalClass().equals("Plant"))
                            .filter(Entity::isAlive)
                            .count();

            long cntAnimal =
                    natureIslandList.stream()
                            .filter(s -> !s.getEntityCharacteristics().getAnimalClass().equals("Plant"))
                            .filter(Entity::isAlive)
                            .count();

            long cntDeadPlants =
                    natureIslandList.stream()
                            .filter(s -> s.getEntityCharacteristics().getAnimalClass().equals("Plant"))
                            .filter(s -> !s.isAlive())
                            .count();

            long cntDeadAnimals =
                    natureIslandList.stream()
                            .filter(s -> !s.getEntityCharacteristics().getAnimalClass().equals("Plant"))
                            .filter(s -> !s.isAlive())
                            .count();

            long cntNewAnimal =
                    natureIslandList.stream()
                            .filter(s -> s instanceof Animal)
                            .map(s -> ((Animal) s))
                            .filter(s -> s.getAmountSpringOff() > 0)
                            .map(Animal::getAmountSpringOff)
                            .reduce(0, Integer::sum);

            String ss = "Кол-во растений = %d, кол-во съеденных растений = %d, кол-во животных = %d " +
                    " исключая умерших\\убитых животных = %d, включая родившихся животных = %d  \n";
            System.out.printf(STATISTIC_INFO, cntPlant, cntDeadPlants, cntAnimal+cntNewAnimal,  cntDeadAnimals, cntNewAnimal);
    }

}

