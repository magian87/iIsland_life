package com.javarush.island_life.classes.entity;

import com.javarush.island_life.classes.Island;
import com.javarush.island_life.classes.enums.DirectionMove;
import com.javarush.island_life.classes.settints.EntityCharacteristics;
import com.javarush.island_life.classes.settints.EntitySettings;

import java.util.Random;

public abstract class Animal extends Entity /*implements Cloneable*/ {
    public DirectionMove getDirectionMove() {
        return directionMove;
    }

    private Position position;

    private String STEP_INFO = "Ходит: %s с клетки: %d\\%d на клетку: %d\\%d ;";
    private String STEP_INFO_CHANGE_DIRECTION_IN_WATHER =
            "Ходит: %s с клетки: %d\\%d дальше идти не куда, смена направления движения на %s ;";
    private String STEP_INFO_CHANGE_DIRECTION_MAX_AMOUNT_ANIMAL_THIS_CLASS =
            "Ходит: %s с клетки: %d\\%d на следующей клетке превышено максимальное количество животных данного вида";

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    private Island island;


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDirectionMove(DirectionMove directionMove) {
        this.directionMove = directionMove;
    }

    private DirectionMove directionMove;

    private EntityCharacteristics entityCharacteristics;

    public Animal(EntityCharacteristics entityCharacteristics) {
        this.directionMove = DirectionMove.RIGHT;
        this.entityCharacteristics = entityCharacteristics;
    }

    public EntityCharacteristics getEntityCharacteristics() {
        return entityCharacteristics;
    }

    public void step() {

        for (int i = 0; i < this.getEntityCharacteristics().getSpeed(); i++) {


            int x = this.position.getX();
            int y = this.position.getY();

            switch (directionMove) {
                case RIGHT -> y++;
                case LEFT -> y--;
                case TOP -> x--;
                case BOTTOM -> x++;
            }

            //А вот так делать правильно? Как получить границы острова?
            //Можно было конечно остров в Position передавать
            //EntitySettings entitySettings = GetEntitySettings.receiveSettings();
            String animalClass = this.entityCharacteristics.getAnimalClass();
            int maxAmountAnimalInCell =
                    island.getEntityIslandCharacteristicsMap().get(animalClass).getMaxAmountAnimalInCell() - 1;

            if (x < 0 || y < 0 || x > island.getHeight() - 1 || y > island.getWidth() - 1
                //|| island.getAmountAnimalClassInCell(Position.positionGetInstance(x,y),animalClass) > maxAmountAnimalInCell

            ) {

                Random random = new Random();

                int xx = random.nextInt(DirectionMove.values().length);
                this.directionMove = DirectionMove.values()[xx];


                //УБРАТЬ ЭТО ДУБЛИРОВАНИЕ КОДА!!!
                String str = STEP_INFO_CHANGE_DIRECTION_IN_WATHER;
                //island.getAmountAnimalClassInCell(Position.positionGetInstance(x,y),animalClass) > maxAmountAnimalInCell?
                //      STEP_INFO_CHANGE_DIRECTION_MAX_AMOUNT_ANIMAL_THIS_CLASS:STEP_INFO_CHANGE_DIRECTION_IN_WATHER;

                System.out.printf(str, animalClass,
                        this.getPosition().getX(), this.getPosition().getY(),
                        DirectionMove.values()[xx].name()
                );

            } else {
                Position position1 = Position.positionGetInstance(x, y);

                System.out.printf(STEP_INFO, animalClass,
                        this.getPosition().getX(), this.getPosition().getY(),
                        position1.getX(), position1.getY());

                //this.setPosition(position1);
                //island.landField.get(this.getPosition()).add(this);

                island.landField.get(this.getPosition()).remove(this);
                this.position = position1;

                island.landField.get(position1).add(this);


            }

        }
    }

    public void eat() {

    }

    public void reproduction() {

    }

    public void choiceOfDirection() {

    }


    @Override
    public String toString() {
        return entityCharacteristics.getEmoji();

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
