package lesson3.task1;

import java.util.*;

/*
 *@autor Kunakbaev Artem
 */
public class MyArray {
    private HashMap<String,Integer> map;

    public MyArray(String[] mass) {
        map = new HashMap<>();
        for (int i = 0; i < mass.length; i++) {
            map.put(mass[i],1);
        }
    }
}

