package com.javarush.island_life.classes;


import com.javarush.island_life.classes.entity.Position;
import com.javarush.island_life.classes.enums.DirectionMove;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MovedAnimal {
    Island island;
    Position position;

    private String STEP_INFO = "Ходит: %s с клетки: %d\\%d на клетку: %d\\%d ;";
    private String STEP_INFO_CHANGE_DIRECTION_IN_WATHER =
            "Ходит: %s с клетки: %d\\%d дальше идти не куда, смена направления движения на %s ;";
    private String STEP_INFO_CHANGE_DIRECTION_MAX_AMOUNT_ANIMAL_THIS_CLASS =
            "Ходит: %s с клетки: %d\\%d на следующей клетке превышено максимальное количество животных данного вида";

    public MovedAnimal(Island island) {
        this.island = island;
    }


    public void moveAnimal() {

     /*   for (AnimalWithPosition value : island.landField2.values()) {

            String animalClass = value.getAnimal().getEntityCharacteristics().getAnimalClass();
            int maxAmountAnimalInCell =
            island.getEntityIslandCharacteristicsMap().get(animalClass).getMaxAmountAnimalInCell()-1;

            for (int i = 0; i < value.getAnimal().getEntityCharacteristics().getSpeed(); i++) {


                DirectionMove directionMove = value.getAnimal().getDirectionMove();
                int x = value.getPosition().getX();
                int y = value.getPosition().getY();

                switch (directionMove) {
                    case RIGHT -> y++;
                    case LEFT -> y--;
                    case TOP -> x--;
                    case BOTTOM -> x++;
                }

                if (x < 0 || y < 0 || x > island.getHeight() - 1 || y > island.getWidth() - 1
                        || island.getAmountAnimalClassInCell(Position.positionGetInstance(x,y),
                        animalClass) > maxAmountAnimalInCell

                ) {
                    Random random = new Random();

                    DirectionMove directionMoveCurrent = value.getAnimal().getDirectionMove();

                    int xx = random.nextInt(DirectionMove.values().length);
                    value.getAnimal().setDirectionMove(DirectionMove.values()[xx]);

                    //УБРАТЬ ЭТО ДУБЛИРОВАНИЕ КОДА!!!
                    String str =
                            island.getAmountAnimalClassInCell(Position.positionGetInstance(x,y),animalClass) > maxAmountAnimalInCell?
                                    STEP_INFO_CHANGE_DIRECTION_MAX_AMOUNT_ANIMAL_THIS_CLASS:STEP_INFO_CHANGE_DIRECTION_IN_WATHER;

                    System.out.printf(str, value.getAnimal().getEntityCharacteristics().getAnimalClass(),
                            value.getPosition().getX(), value.getPosition().getY(),
                            DirectionMove.values()[xx].name()
                    );

                } else {
                    Position position1 = Position.positionGetInstance(x, y);
                    System.out.printf(STEP_INFO, value.getAnimal().getEntityCharacteristics().getAnimalClass(),
                            value.getPosition().getX(), value.getPosition().getY(),
                            position1.getX(), position1.getY());
                    value.setPosition(position1);
                }

            }
            System.out.println();
        }*/
    }

}

