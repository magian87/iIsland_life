package com.javarush.island_life.classes;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island_life.classes.comparators.EntityComparator;
import com.javarush.island_life.classes.entity.Animal;
import com.javarush.island_life.classes.entity.Entity;
import com.javarush.island_life.classes.entity.Position;
import com.javarush.island_life.classes.settints.EatCharacteristics;
import com.javarush.island_life.classes.settints.EntityIslandCharacteristics;
import com.javarush.island_life.classes.settints.EntityProducer;
import com.javarush.island_life.classes.settints.EntitySettings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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

    private List<Entity> natureIslandList = new ArrayList<>();

    public Map<String, EntityIslandCharacteristics> getEntityIslandCharacteristicsMap() {
        return entityIslandCharacteristicsMap;
    }


    private Map<String, EntityIslandCharacteristics> entityIslandCharacteristicsMap
            = new HashMap<>();


    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            entitySettings = objectMapper.readValue(
                    Files.newBufferedReader(Path.of("settings//entity2.json")), EntitySettings.class);


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



    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }



    public long receiveAmountAnimalClassInCell(Position position, String animalClass) {
         return natureIslandList.stream()
                 .filter(s-> s.getEntityCharacteristics().getAnimalClass().equals(animalClass))
                 .count();
    }

    public void firstFillEntity() {
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

                } while (receiveAmountAnimalClassInCell(position1, classAnimal) > maxAmountAnimalInCell);

                Entity entity = entityProducer.createEntity(classAnimal);

                entity.setPosition(position1);
                entity.setIsland(this);

                natureIslandList.add(entity);
            }
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
        /*
                double result = natureIslandList.stream()
                .collect(Collectors.groupingBy(
                        Entity::getPosition, Collectors.counting()))
                .entrySet()
                .stream()
                .map(s -> s.getValue())
                .max(Comparator.comparing(Long::valueOf))
                .orElse(0L);
        * */
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

            stringBuilder.append("[" + i + "   ".repeat(maxLength>0?maxLength/2:0) + "]");
            //stringBuilder.append("[" + i + "   ".repeat(maxLength) + "]");
        }
        System.out.println(stringBuilder);

        for (int i = 0; i < height; i++) {
            System.out.format("%3d", i);
            for (int j = 0; j < width; j++) {
                Position position = Position.positionGetInstance(i, j);
                String curEmojis = receiveEmojisByPosition(position);
                // делю на два, т.к. длина одного эмоджи 2 знака, можно было конечно еще один стрим сделать...
                String ss = "[%s" + "  ".repeat(maxLength - curEmojis.length()/2) + "]";
                System.out.printf(ss, curEmojis);
            }
            System.out.println();
        }
    }

    public int receiveAmountAnimal(){
        long result;
        result = natureIslandList.stream()
                .filter(s->!s.getEntityCharacteristics().getAnimalClass().equals("Plant"))
                .count();
        return ((int) result);
    }

    public void nextStep() {
        for (Entity entity : natureIslandList) {
            if (entity instanceof Animal animal){
                animal.step();
            }
        }
    }

    public void removeNotAliveEntities() {
        natureIslandList.removeIf(entity -> !entity.isAlive());
    }

    public Entity receivePairForReproduction(Entity entity){
        return natureIslandList.stream()
                .filter(s->s.getPosition().equals(entity.getPosition()))
                .filter(s->s.getEntityCharacteristics().getAnimalClass()
                        .equals(entity.getEntityCharacteristics().getAnimalClass()))
                .filter(s->s.getEntityCharacteristics().getCurrentSaturation()!=0)
                .filter(s->!s.equals(entity))
                .findFirst()
                .orElse(null);
    }



/*
    public void receiveTTT() {
        System.out.println("@@@@@@");

*/
/*        List<Entity> ll =
        natureIslandList.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getPosition(), Collectors.counting()));*//*



        System.out.println(natureIslandList.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getPosition(), Collectors.counting()))
                .entrySet()
                .stream()
                .map(s -> s.getValue())
                .max(Comparator.comparing(Long::valueOf))
                .get());



                */
/*System.out.println(natureIslandList.stream()
                        .collect(Collectors.groupingBy(s -> s.getPosition()))
                );*//*


        System.out.println(natureIslandList.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getEntityCharacteristics().getAnimalClass(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(s1 -> s1.getKey(), s1 -> s1.getValue())));
        //.forEach((ss, count) -> System.out.println(ss + " " + count));

    }

    public void test() {
       */
/* System.out.println(natureIslandList.stream()
                .collect(Collectors.groupingBy(s -> s.getEntityCharacteristics().getAnimalClass())));*//*


        //Уникальные сущности
        natureIslandList.stream()
                .map(s -> s.getEntityCharacteristics().getAnimalClass())
                .distinct()
                //.sorted(Comparator.comparing(s->s.com))
                .forEach(System.out::println);

        //Map количество сущностей каждого типа

        System.out.println(natureIslandList.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getEntityCharacteristics().getAnimalClass(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(s1 -> s1.getKey(), s1 -> s1.getValue())));
        //.forEach((ss, count) -> System.out.println(ss + " " + count));

        natureIslandList.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getEntityCharacteristics().getAnimalClass(), Collectors.counting()))
                //.entrySet()
                //.stream()
                //.collect(Collectors.toMap(s1 -> s1.getKey(), s1 -> s1.getValue())));
                .forEach((ss, count) -> System.out.println(ss + " " + count));


        Position position = Position.positionGetInstance(0, 0);

        Map<String, Long> ll =
                natureIslandList.stream()
                        .filter(s -> s.getPosition().equals(position))
                        .collect(Collectors.groupingBy(
                                s -> s.getEntityCharacteristics().getAnimalClass(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(s -> s.getKey(), s -> s.getValue()));
        System.out.println(ll);

        System.out.println("!!!");


        System.out.println(natureIslandList.stream()
                .filter(s -> s.getPosition().equals(position))
                .sorted(new EntityComparator())
                .map(s -> s.getEntityCharacteristics().getEmoji())
                .collect(Collectors.joining(", ")));

        //.toList());






*/
/*        System.out.println(natureIslandList.stream()
                .collect(Collectors.toMap(
                        s -> s.getPosition(),
                        s -> s.getEntityCharacteristics().getAnimalClass(),
                        (a, b) -> String.join(", ", a, b)
                )));*//*

    }
*/


/*    public void removeDeadAnimal(){
        List<Animal> animalList = receiveAnimal();
        for (Animal animal : animalList) {
            if (animal.isDead()){
                landField.get(animal.getPosition()).remove(animal);
            }
        }
    }*/

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

    /*
    public void viewEntityByIsland() {
        int max = 0;
        for (Position position : landField.keySet()) {
            int size = landField.get(position).size();
            max = Math.max(max, size);
        }
        System.out.println("---------------------------------");
        StringBuilder stringBuilder0 = new StringBuilder("   ");
        for (int i = 0; i < width; i++) {
            stringBuilder0.append("[" + i + "  ".repeat(max == 0 ? 0 : max - 1) + " ]");
        }
        System.out.println(stringBuilder0);

        for (int i = 0; i < height; i++) {
            System.out.format("%3d", i);
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
            //System.out.print(i);
            System.out.println();
        }
    }
*/

}
