package com.javarush.islandlife.classes.entity;

import java.util.Map;
import java.util.stream.Stream;

public class Refactoring {
    public Refactoring() {
    }



/*    public Stream keys(Map<String, String> map, String value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                //.map(entry -> entry.getValue())
                .map(Map.Entry::getKey);
    }*/

    //jsonNode = objectMapper.readTree(Files.newBufferedReader(Path.of("settings//settingsIsland.json")));
    //this.height = jsonNode.get("height").intValue();
    //this.width = jsonNode.get("width").intValue();

    /*Path path = Path.of("files//animalConfig1.properties");

        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader(path.toAbsolutePath().toString())) {
            properties.load(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
*/

/*
    Хищник не должен есть умерающее (умершее животное)
     //2. Получить список животных\растений на той же клетке (Убрать животных того же вида)
            } catch (IOException e) {
            throw new RuntimeException("Не найден файл настроек, программа не будет запущена!");
            x,y, i,j
            в размножении убрать дублирование кода


,

    * */
}
