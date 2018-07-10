package lesson3.task1;

import java.util.HashMap;

/*
 *@autor Kunakbaev Artem
 */
public class UniArray {
    private HashMap<String, Integer> map;

    public UniArray(String[] mass) {
        map = new HashMap<>();
        for (String m: mass) map.put(m, map.getOrDefault(m, 0) + 1);
    }

    public void showArray(){
        System.out.println(map);
    }
}
