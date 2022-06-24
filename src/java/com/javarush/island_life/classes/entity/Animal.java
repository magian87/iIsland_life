package com.javarush.island_life.classes.entity;

import com.diogonunes.jcolor.Attribute;
import com.javarush.island_life.classes.Island;
import com.javarush.island_life.classes.entity.plant.Plant;
import com.javarush.island_life.classes.enums.DirectionMove;
import com.javarush.island_life.classes.settints.EntityCharacteristics;
import com.javarush.island_life.classes.settints.EntityProducer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.javarush.island_life.classes.ConstantIsland.*;

public abstract class Animal extends Entity /*implements Cloneable*/ {
    public DirectionMove getDirectionMove() {
        return directionMove;
    }

    private int amountStepWithoutSaturation;
    private int amountParented;
    private int amountStepWithoutParent;

    public int getAmountSpringOff() {
        return amountSpringOff;
    }

    public void setAmountSpringOff(int amountSpringOff) {
        this.amountSpringOff = amountSpringOff;
    }

    private int amountSpringOff;

    public Animal(EntityCharacteristics entityCharacteristics) {
        super(entityCharacteristics);
        this.directionMove = DirectionMove.RIGHT;
        //Это нормально так описывать свойства, одни из настроек, другие для служебного пользования класса...????
        this.amountStepWithoutSaturation = 0;
        this.amountParented = 0;
        this.amountStepWithoutParent = 0;
        this.amountSpringOff = 0;
    }

    public int getAmountStepWithoutSaturation() {
        return amountStepWithoutSaturation;
    }

    public void setAmountStepWithoutSaturation(int amountStepWithoutSaturation) {
        this.amountStepWithoutSaturation = amountStepWithoutSaturation;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    private Island island;

    public void setDirectionMove(DirectionMove directionMove) {
        this.directionMove = directionMove;
    }

    private DirectionMove directionMove;


    public void step() {
        for (int i = 0; i < this.getEntityCharacteristics().getSpeed(); i++) {
            if (this.isAlive()) {
                this.eat();

                int x = this.getPosition().getX();
                int y = this.getPosition().getY();

                switch (directionMove) {
                    case RIGHT -> y++;
                    case LEFT -> y--;
                    case TOP -> x--;
                    case BOTTOM -> x++;
                }

                String animalClass = this.getEntityCharacteristics().getAnimalClass();
                int maxAmountAnimalInCell =
                        island.getEntityIslandCharacteristicsMap().get(animalClass).getMaxAmountAnimalInCell() - 1;

                //Уменьшение насыщения
                double currentSaturation = this.getEntityCharacteristics().getCurrentSaturation();
                if (currentSaturation > 0) {
                    double reductionSaturation = (this.getEntityCharacteristics().getSaturation() * REDUCTION_SATURATION / this.getEntityCharacteristics().getSpeed());
                    this.getEntityCharacteristics().setCurrentSaturation(Math.max(currentSaturation - reductionSaturation, 0));
                }
                //Если насыщение ==0, подсчет кол-во шагов без пищи
                if (currentSaturation == 0) {
                    this.amountStepWithoutSaturation++;
                }

                //Если кол-во шагов без пищи > DAYS_HUNGRY_DEAD - 1 то пометка животного - умер
                if (this.amountStepWithoutSaturation > DAYS_HUNGRY_DEAD - 1) {
                    this.setIsAlive(false);
                    //System.out.println(colorize(this.getEntityCharacteristics().getAnimalClass() + this.getPosition() + " умер от голода ;"
                    //        , Attribute.BRIGHT_CYAN_TEXT(), Attribute.NONE()));
                    return; //Надо ли убирать и по другому выстраивать логику?

                }


                //
                if (x < 0 || y < 0 || x > island.getHeight() - 1 || y > island.getWidth() - 1
                        || island.receiveAmountAnimalClassInCell(Position.positionGetInstance(x, y), animalClass) > maxAmountAnimalInCell

                ) {

                    this.choiceOfDirection();
                    /*System.out.printf(STEP_INFO_CHANGE_DIRECTION_IN_WATHER, animalClass,
                            this.getPosition().getX(), this.getPosition().getY(),
                            DirectionMove.values()[xx].name(),
                            this.getEntityCharacteristics().getCurrentSaturation()
                    );*/

                } else {
                    Position position1 = Position.positionGetInstance(x, y);
                    /*System.out.printf(STEP_INFO, animalClass,
                            this.getPosition().getX(), this.getPosition().getY(),
                            position1.getX(), position1.getY(),
                            this.getEntityCharacteristics().getCurrentSaturation());*/
                    this.setPosition(position1);
                    this.eat();
                }
                //Если последний шаг и насыщение больше 0 попробовать размножиться
                if (i == this.getEntityCharacteristics().getSpeed() - 1) {
                    if (this.amountStepWithoutParent > 0) {
                        this.amountStepWithoutSaturation--;
                    }
                    this.reproduction();
                }
            }
        }
    }

    public void eat() {
        //1. Проверить если насыщение меньше максимума то продолжать
        if (this.getEntityCharacteristics().getCurrentSaturation() < this.getEntityCharacteristics().getSaturation()) {
            //2. Получить список животных\растений на той же клетке (Убрать животных того же вида желательно,
            // т.к. сейчас нет прецедентов)
            List<Entity> entityList = island.receiveEntitiesByPosition(this.getPosition());
            //3. Получить мэп вероятности поедания для хищника или травоядного или и того и того
            Map<String, Map<String, Integer>> probabilityKillMap = island.receiveProbabilityKillMap();
            //4. Цикл по  найденному списку
            for (Entity entity : entityList) {
                //5. Если насыщение=максимальное насыщение выйти из процедуры, иначе съесть других животных
                if (this.getEntityCharacteristics().getCurrentSaturation() == this.getEntityCharacteristics().getSaturation()) {
                    return;
                    //!!!!!!!!!!!!!!!!!А так нормально вообще выходить?

                }
                //6. Животное может есть данную сущность (животное, растение), кинуть вероятность полученную из мапы

                String curAnimalClass;
                double curWeight;
                if (entity instanceof Animal animal) {
                    curAnimalClass = animal.getEntityCharacteristics().getAnimalClass();
                    curWeight = animal.getEntityCharacteristics().getWeight();
                } else if (entity instanceof Plant plant) {
                    curAnimalClass = plant.getEntityCharacteristics().getAnimalClass();
                    curWeight = plant.getEntityCharacteristics().getWeight();
                } else {
                    throw new RuntimeException("Объект неизвестного типа");
                }

                int probability = probabilityKillMap
                        .get(this.getEntityCharacteristics().getAnimalClass()).get(curAnimalClass);

                if (entity.isAlive()) {
                    if (probability > 0) {
                        int probability1 = ThreadLocalRandom.current().nextInt(probability);
                        //   Если вероятность от 0 до вероятности из списка, то насытить животное
                        //   math.min(текущее насыщение + вес животного, максимальное насыщение)
                        if (0 <= probability1 && probability1 <= probability) {

                            this.getEntityCharacteristics()
                                    .setCurrentSaturation(
                                            Math.min(this.getEntityCharacteristics().getCurrentSaturation() + curWeight,
                                                    this.getEntityCharacteristics().getSaturation()));
                            //System.out.println(colorize(this.getEntityCharacteristics().getAnimalClass() + " съел " + curAnimalClass + "  текущее насыщение: " + this.getEntityCharacteristics().getCurrentSaturation(), Attribute.BLUE_TEXT(), Attribute.NONE()));
                            //6. Пометить животное как убитое, удалить из списка
                            entity.setIsAlive(false);

                        }
                    }
                }
            }

        }
    }

    private double receiveSaturationByAnimal(Animal animal) {
        return animal.getEntityCharacteristics().getCurrentSaturation();
    }

    public void reproduction() {
        //Животное может размножиться только под конец хода
        //Животное может размножиться если насыщение у обоих животных равно 0
        //Количество размножений за игру указывается в настройках - доработать

        //1. Проверить если насыщение больше 0
        if (receiveSaturationByAnimal(this) > 0 && this.amountStepWithoutParent == 0) {
            //2. Проверить есть ли другие животные такого же типа на этой клетке с насыщением больше 0
            Entity entity = island.receivePairForReproduction(this);
            if (entity != null) {
                //3. Получить кол-во детенышей по рэндому от кол-во возможного приплода
                int springOff = ThreadLocalRandom.current().nextInt(this.getEntityCharacteristics().getAmountOffspring());
                int maxAmountAnimalClassInCell =
                        island.getEntityIslandCharacteristicsMap().
                                get(this.getEntityCharacteristics().getAnimalClass()).getMaxAmountAnimalInCell();
                //4. Если кол-во животных данного типа + количество детенышей не превышает максимально
                //   возможного числа животных этого типа
                if (island.receiveAmountAnimalClassInCell(this.getPosition(),
                        this.getEntityCharacteristics().getAnimalClass()) + springOff <= maxAmountAnimalClassInCell) {
                    this.amountParented = this.amountParented + 1;
                    //5. Установить количество ходов без размножения
                    this.amountStepWithoutParent = AMOUNT_WITHOUT_REPRODUCTION;
                    //6. Поставить пометку о потомстве
                    this.amountSpringOff = springOff;

                    Animal animal = ((Animal) entity);
                    animal.amountParented = animal.amountParented + 1;
                    animal.amountStepWithoutParent = AMOUNT_WITHOUT_REPRODUCTION;
                }

            }

        }
    }

    public void choiceOfDirection() {
        int xx;
        do {
            xx = ThreadLocalRandom.current().nextInt(DirectionMove.values().length);

        } while (DirectionMove.values()[xx] == this.directionMove);
        this.directionMove = DirectionMove.values()[xx];
    }


    @Override
    public String toString() {
        return this.getEntityCharacteristics().getEmoji();

/*        return "Animal{" +
                "weight=" + weight +
                ", speed=" + speed +
                ", appetite=" + appetite +
                ", name='" + name + '\'' +
                ", emoji='" + emoji + '\'' +
                '}';*/


    }
}

