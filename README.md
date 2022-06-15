# iIsland_life
#Simulation of Island life

Что сделано:

1. Реализация иерархии классов
2. Хранение процентной вероятности поедания животных
3. Инициализация поля (из настроек)
4. Животное двигается на определенное количество клеток
5. Если дальше двигаться не куда, либо превышено кол-во животных данного вида на клетке, то животное меняет направление


Что не сделано:
1. Уменьшение насыщения для животного при шаге
2. Насыщение животного
3. Размножение
4. Восстановление травы
5. Сбор статистики по полю
6. Смерть животного
7. Многопоточность

##Все настройки берутся из файла settings//entity2.json
1. Характеристика острова:
   "islandCharacteristics": [
   {
   "height": 5,
   "width": 5
   }
   ]

2. Характеристики сущности по отношению к острову 
   "entityIslandCharacteristics": [
   {
   "animalClass": "Wolf",
   "maxAmountAnimalInCell": 5,
   "maxAmountOfThisAnimal": 10
   }]
   (animalClass переименовать в entityClass)
   Вероятно перенесу в характеристики острова (п.1)
4. Вероятностная таблица насыщения животного
      "eatCharacteristics": [
      {
      "predator": "Wolf",
      "wolf" : 0,
      "snake": 0,
      "fox": 0,
      "bear": 0,
      "eagle": 0,
      "horse": 0,
      "deer": 15,
      "rabbit": 60,
      "mouse": 80,
      "goat": 60,
      "sheep": 70,
      "buffalo": 10,
      "duck": 40,
      "caterpillar": 0,
      "plant": 0
      }]

5. Характеристики сущности (Трава, животные (травоядные, хищники))
"entityCharacteristics": [
   {
   "animalClass": "Plant",
   "weight":200,
   "speed":0,
   "saturation":0,
   "currentSaturation": 0,
   "emoji":"\uD83C\uDF31",
   "className": "plant"
   }]

##Немного о реализации
Главный класс Entity - это базовый класс, на основе него создаются животные и растения
<
public class Entity {
private EntityCharacteristics entityCharacteristics;
Position position; 
Island island; ... }
'''Java
Position - это позиция на острове, класс сделан Singleton-ом, видимо тоже под доработку...
public static Position positionGetInstance(int x, int y) {
Position position = receiveInstance(x,y);
if (position!=null) {
return position;
} else {
Position positionNew = new Position(x,y);
positionList.add(positionNew);
return positionNew;
}
}
'''

Что мне не нравится:
1. У класса Entity пришлось сделать поле - island (ссылку на остров). Для того, что бы получать характеристики острова (длина, ширина), кол-во животных на клетке (Position)
2. Как исправить. Сделать Map<Island, List<Position>> и тогда все будет работать... В т.ч. с горизонтальным масштабированием. Этот момент еще нужно продумать
3. Класс животное пишет в класс Island. Как исправить? У животного меняю только Position. После того как животное сходило, привожу в соответствие положение животного на острове (Map<Position, List<Entity>> landField), вызываю метод island.reUpdateIsland
4. Логгер по перемещению животного находится в animal.step(), как сделать по другому незнаю
   
public class Entity {
   private EntityCharacteristics entityCharacteristics;
   Position position;
   Island island;

Запуск проекта осуществляется в модуле: com.javarush.island_life.classes.Main

Проект скомпилировал под Java 17
файл настроек прописан так: "settings//entity.json"

##Все ли мои утверждения являются верными, может я в чем то ошибаюсь?