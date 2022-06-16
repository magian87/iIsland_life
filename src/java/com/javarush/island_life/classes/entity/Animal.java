package com.javarush.island_life.classes.entity;

import com.javarush.island_life.classes.Island;
import com.javarush.island_life.classes.enums.DirectionMove;
import com.javarush.island_life.classes.settints.EntityCharacteristics;
import com.javarush.island_life.classes.settints.EntitySettings;

import java.util.Random;

import static com.javarush.island_life.classes.ConstantIsland.*;

public abstract class Animal extends Entity /*implements Cloneable*/ {
    public DirectionMove getDirectionMove() {
        return directionMove;
    }

    private int amountStepWithoutSaturation;
    private boolean isDead;

    public Animal(EntityCharacteristics entityCharacteristics) {
        super(entityCharacteristics);
        this.directionMove = DirectionMove.RIGHT;
        //Это нормально так описывать свойства, одни из настроек, другие для служебного пользования класса...????
        this.amountStepWithoutSaturation = 0;
        this.isDead = false;
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


            int x = this.getPosition().getX();
            int y = this.getPosition().getY();

            switch (directionMove) {
                case RIGHT -> y++;
                case LEFT -> y--;
                case TOP -> x--;
                case BOTTOM -> x++;
            }

            //А вот так делать правильно? Как получить границы острова?
            String animalClass = this.getEntityCharacteristics().getAnimalClass();
            int maxAmountAnimalInCell =
                    island.getEntityIslandCharacteristicsMap().get(animalClass).getMaxAmountAnimalInCell() - 1;

            double currentSaturation = this.getEntityCharacteristics().getCurrentSaturation();
            if (currentSaturation > 0) {
                double reductionSaturation = Math.ceil(this.getEntityCharacteristics().getSaturation() * REDUCTION_SATURATION / this.getEntityCharacteristics().getSpeed());
                this.getEntityCharacteristics().setCurrentSaturation(Math.max(currentSaturation - reductionSaturation, 0));
            }
            if (currentSaturation == 0) {
                this.amountStepWithoutSaturation++;
            }

            if (this.amountStepWithoutSaturation > DAYS_HUNGRY_DEAD-1) {
                this.isDead = true;
            }

            if (!this.isDead) {
                this.eat();


                if (x < 0 || y < 0 || x > island.getHeight() - 1 || y > island.getWidth() - 1
                        || island.getAmountAnimalClassInCell(Position.positionGetInstance(x, y), animalClass) > maxAmountAnimalInCell

                ) {

                    Random random = new Random();
                    int xx;
                    do {
                        xx = random.nextInt(DirectionMove.values().length);

                    } while (DirectionMove.values()[xx] == this.directionMove);

                    this.directionMove = DirectionMove.values()[xx];

                    //УБРАТЬ ЭТО ДУБЛИРОВАНИЕ КОДА!!!
                    //String str = STEP_INFO_CHANGE_DIRECTION_IN_WATHER;
                    //island.getAmountAnimalClassInCell(Position.positionGetInstance(x,y),animalClass) > maxAmountAnimalInCell?
                    //      STEP_INFO_CHANGE_DIRECTION_MAX_AMOUNT_ANIMAL_THIS_CLASS:STEP_INFO_CHANGE_DIRECTION_IN_WATHER;

                    System.out.printf(STEP_INFO_CHANGE_DIRECTION_IN_WATHER, animalClass,
                            this.getPosition().getX(), this.getPosition().getY(),
                            DirectionMove.values()[xx].name(),
                            this.getEntityCharacteristics().getCurrentSaturation()
                    );
                    //Логгер должен быть отдельно, как его перенести в другое место, если здесь происходит перемещение животного?

                } else {
                    Position position1 = Position.positionGetInstance(x, y);

                    System.out.printf(STEP_INFO, animalClass,
                            this.getPosition().getX(), this.getPosition().getY(),
                            position1.getX(), position1.getY(),
                            this.getEntityCharacteristics().getCurrentSaturation());

                    //А так вообще нормально, геттером записывать. обсуждали. Уберу отсюда island.getLandField
                    //см. island.reUpdateIsland(), буду задавать позицию животного, и в соответсвии с этой позицией
                    //приводить остров к актуальному состоянию
                    //но после того, как остальное будет сделано, т.к. времени уже мало...
                    System.out.println(island.getLandField().get(this.getPosition()).remove(this));
                    this.setPosition(position1);

                    island.getLandField().get(position1).add(this);


                }
            } else {
                    System.out.print(this.getEntityCharacteristics().getAnimalClass() + " умер от голода " );
                    System.out.println(island.getLandField().get(this.getPosition()).remove(this));
                    return; //Надо ли убирать и по другому выстраивать логику?

            }
        }
        //System.out.println();
    }

    public void eat() {


    }

    public void reproduction() {

    }

    public void choiceOfDirection() {

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

    /*public Animal clone() throws CloneNotSupportedException {
        Animal animal = (Animal) super.clone();
        //А надо ли, если у животных одного вида будут одинаковые параметры?
        //animal.se
        return animal;

    }*/
}
