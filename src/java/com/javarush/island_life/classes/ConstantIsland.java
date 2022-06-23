package com.javarush.island_life.classes;

public class ConstantIsland
{
    public static String STEP_INFO = "Ходит: %s с клетки: %d\\%d на клетку: %d\\%d; насыщение = %3.3f; \n";
    public static String STEP_INFO_CHANGE_DIRECTION_IN_WATHER =
            "Ходит: %s с клетки: %d\\%d дальше идти не куда, смена направления движения на %s; насыщение = %3.3f ; \n";

    public static String STATISTIC_INFO = "Кол-во растений = %d, кол-во съеденных растений = %d, кол-во животных = %d " +
            " исключая умерших\\убитых животных = %d, включая родившихся животных = %d  \n";

    public static double REDUCTION_SATURATION = 0.33;
    public static int DAYS_HUNGRY_DEAD = 4;
    public static int AMOUNT_WITHOUT_REPRODUCTION = 3;
}
