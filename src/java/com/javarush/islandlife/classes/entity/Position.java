package com.javarush.islandlife.classes.entity;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;


//Это не Singleton
public final class Position {
    private int x;
    private int y;
    private static final Position position = new Position();
    private static List<Position> positionList = new CopyOnWriteArrayList<>();


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

