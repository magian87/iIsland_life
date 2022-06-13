package com.javarush.island_life.classes;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Main {
    public static void main(String[] args) {
        System.out.println(colorize("Hello!!!", Attribute.YELLOW_TEXT(), Attribute.NONE()));
    }
}
