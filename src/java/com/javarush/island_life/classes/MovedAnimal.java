package com.javarush.island_life.classes;


import com.javarush.island_life.classes.entity.Position;
import com.javarush.island_life.classes.enums.DirectionMove;

import java.util.List;
import java.util.Random;

public class MovedAnimal {
    Island island;
    Position position;

    public MovedAnimal(Island island) {
        this.island = island;
    }


    public void moveAnimal() {
        for (AnimalWithPosition value : island.landField2.values()) {

            String str = "Ходит: %s с клетки: %d\\%d на клетку: %d\\%d ;";
            String str2 = "Ходит: %s с клетки: %d\\%d дальше идти не куда, смена направления движения на %s ;";
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

                if (x < 0 || y < 0 || x > island.getHeight() - 1 || y > island.getWidth() - 1) {
                    Random random = new Random();

                    DirectionMove directionMoveCurrent = value.getAnimal().getDirectionMove();

                    int xx = random.nextInt(DirectionMove.values().length);
                    value.getAnimal().setDirectionMove(DirectionMove.values()[xx]);


                    System.out.printf(str2, value.getAnimal().getEntityCharacteristics().getAnimalClass(),
                            value.getPosition().getX(), value.getPosition().getY(),
                            DirectionMove.values()[xx].name()
                    );

                } else {
                    Position position1 = Position.positionGetInstance(x, y);
                    System.out.printf(str, value.getAnimal().getEntityCharacteristics().getAnimalClass(),
                            value.getPosition().getX(), value.getPosition().getY(),
                            position1.getX(), position1.getY());
                    value.setPosition(position1);
                }

            }
            System.out.println();
        }
    }
}

