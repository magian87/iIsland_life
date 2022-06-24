package com.javarush.islandlife.classes.settints;


import com.javarush.islandlife.classes.entity.Entity;
import com.javarush.islandlife.classes.entity.herbivore_animal.*;
import com.javarush.islandlife.classes.entity.herbivore_animal.*;
import com.javarush.islandlife.classes.entity.plant.Plant;
import com.javarush.islandlife.classes.entity.predator_animal.*;
import com.javarush.islandlife.classes.entity.predator_animal.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityProducer {
    private Map<String, EntityCharacteristics> entityCharacteristicsMap = new HashMap<>();


    public EntityProducer(EntityCharacteristics[] entityCharacteristics) {
        final String PACKAGE_NAME = "com.javarush.islandlife.classes.entity.%s.%s";
        for (EntityCharacteristics currentCharacteristic : entityCharacteristics) {
            try {
                Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(
                        String.format(PACKAGE_NAME, currentCharacteristic.getClassName(), currentCharacteristic.getAnimalClass()));

                entityCharacteristicsMap.put(currentCharacteristic.getAnimalClass(), currentCharacteristic.clone());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //Вот эта простыня мне не особо нравится, действительно нужно создать одно животное, но ему назначить разный вид и эмоджи
    public Entity createEntity(String nameAnimal){
        if (nameAnimal.equals(Plant.class.getSimpleName())) {
            return createPlant();
        } else if (nameAnimal.equals(Bear.class.getSimpleName())){
            return createBear();
        } else if (nameAnimal.equals(Wolf.class.getSimpleName())){
            return createWolf();
        } else if (nameAnimal.equals(Snake.class.getSimpleName())){
            return createSnake();
        } else if (nameAnimal.equals(Eagle.class.getSimpleName())){
            return createEagle();
        } else if (nameAnimal.equals(Fox.class.getSimpleName())){
            return createFox();
        } else if (nameAnimal.equals(Horse.class.getSimpleName())){
            return createHorse();
        } else if (nameAnimal.equals(Deer.class.getSimpleName())){
            return createDeer();
        } else if (nameAnimal.equals(Rabbit.class.getSimpleName())){
            return createRabbit();
        } else if (nameAnimal.equals(Mouse.class.getSimpleName())){
            return createMouse();
        } else if (nameAnimal.equals(Goat.class.getSimpleName())){
            return createGoat();
        } else if (nameAnimal.equals(Sheep.class.getSimpleName())){
            return createSheep();
        } else if (nameAnimal.equals(Boar.class.getSimpleName())){
            return createBoar();
        } else if (nameAnimal.equals(Buffalo.class.getSimpleName())){
            return createBuffalo();
        } else if (nameAnimal.equals(Duck.class.getSimpleName())){
            return createDuck();
        } else if (nameAnimal.equals(Caterpillar.class.getSimpleName())){
            return createCaterpillar();
        }
        else
            throw new RuntimeException("Вы пытаетесь создать не предусмотренное животное");
    }

    public Set<String> receiveEntity(){
        return entityCharacteristicsMap.keySet().stream().collect(Collectors.toSet());
        //Warning:(45, 50) Can be replaced with 'java.util.HashSet' constructor
        //Что не нравится Git???
    }

    public Plant createPlant(){
        return new Plant(entityCharacteristicsMap.get(Plant.class.getSimpleName()).clone());
    }
    public Fox createFox(){
        return new Fox(entityCharacteristicsMap.get(Fox.class.getSimpleName()).clone());
    }

    public Bear createBear(){
        return new Bear(entityCharacteristicsMap.get(Bear.class.getSimpleName()).clone());
    }

    public Wolf createWolf(){
        return new Wolf(entityCharacteristicsMap.get(Wolf.class.getSimpleName()).clone());
    }

    public Eagle createEagle(){
        return new Eagle(entityCharacteristicsMap.get(Eagle.class.getSimpleName()).clone());
    }

    public Snake createSnake(){
        return new Snake(entityCharacteristicsMap.get(Snake.class.getSimpleName()).clone());
    }
    public Horse createHorse(){
        return new Horse(entityCharacteristicsMap.get(Horse.class.getSimpleName()).clone());
    }

    public Deer createDeer(){
        return new Deer(entityCharacteristicsMap.get(Deer.class.getSimpleName()).clone());
    }

    public Rabbit createRabbit(){
        return new Rabbit(entityCharacteristicsMap.get(Rabbit.class.getSimpleName()).clone());
    }

    public Mouse createMouse(){
        return new Mouse(entityCharacteristicsMap.get(Mouse.class.getSimpleName()).clone());
    }

    public Goat createGoat(){
        return new Goat(entityCharacteristicsMap.get(Goat.class.getSimpleName()).clone());
    }

    public Sheep createSheep(){
        return new Sheep(entityCharacteristicsMap.get(Sheep.class.getSimpleName()).clone());
    }

    public Boar createBoar(){
        return new Boar(entityCharacteristicsMap.get(Boar.class.getSimpleName()).clone());
    }

    public Buffalo createBuffalo(){
        return new Buffalo(entityCharacteristicsMap.get(Buffalo.class.getSimpleName()).clone());
    }

    public Duck createDuck(){
        return new Duck(entityCharacteristicsMap.get(Duck.class.getSimpleName()).clone());
    }

    public Caterpillar createCaterpillar(){
        return new Caterpillar(entityCharacteristicsMap.get(Caterpillar.class.getSimpleName()).clone());
    }








}
