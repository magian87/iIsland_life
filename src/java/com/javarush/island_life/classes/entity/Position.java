package com.javarush.island_life.classes.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

//Правильно ли применен Singltone?
//Можно отказаться от Equals & HashCode?
public class Position {
    private int x;
    private int y;
    private static final Position position = new Position();

    public Position() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    private static List<Position> positionList = new LinkedList<>();

    //Singleton
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

    private static Position receiveInstance(int x, int y) {
        for (Position position : positionList) {
            if (position.x == x && position.y == y){
                return position;
            }
        }
        return null;
    }

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

