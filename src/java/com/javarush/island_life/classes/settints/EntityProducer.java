package com.javarush.island_life.classes.settints;


import com.javarush.island_life.classes.entity.Animal;
import com.javarush.island_life.classes.entity.predator_animal.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityProducer {
    private Map<String, EntityCharacteristics> entityCharacteristicsMap = new HashMap<>();

    public EntityProducer(EntityCharacteristics[] entityCharacteristics) {
        for (EntityCharacteristics currentCharacteristic : entityCharacteristics) {
            try {
                Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(
                        "com.javarush.island_life.classes.entity.predator_animal."+
                                currentCharacteristic.getAnimalClass());

                entityCharacteristicsMap.put(currentCharacteristic.getAnimalClass(), currentCharacteristic.clone());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Animal createEntity(String nameAnimal){
        if (nameAnimal.equals(Bear.class.getSimpleName())){
            return createBear();
        } else if (nameAnimal.equals(Wolf.class.getSimpleName())){
            return createWolf();
        } else if (nameAnimal.equals(Snake.class.getSimpleName())){
            return createSnake();
        } else if (nameAnimal.equals(Eagle.class.getSimpleName())){
            return createEagle();
        } else if (nameAnimal.equals(Fox.class.getSimpleName())){
            return createFox();
        } else
            throw new RuntimeException("Вы пытаетесь создать не предусмотренное животное");
    }

    public Set<String> receiveEntity(){
        return entityCharacteristicsMap.keySet().stream().collect(Collectors.toSet());
        //Warning:(45, 50) Can be replaced with 'java.util.HashSet' constructor
        //Что не нравится Git???
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


}
