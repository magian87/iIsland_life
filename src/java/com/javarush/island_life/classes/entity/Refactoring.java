package com.javarush.island_life.classes.entity;

import java.util.Map;
import java.util.stream.Stream;

public class Refactoring {
    public Refactoring() {
    }

    public Stream keys(Map<String, String> map, String value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                //.map(entry -> entry.getValue())
                .map(Map.Entry::getKey);
    }

/*
    Змея зависает в крайней клетке, нужно хранить что попытка хода не удалась



    * */
}
