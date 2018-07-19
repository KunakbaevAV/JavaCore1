package lesson3.task1;

import java.util.HashMap;

/*
 *@autor Kunakbaev Artem
 */
public class App {
    static String[] words = new String[]{
            "яблоко",
            "груша",
            "манго",
            "дыня",
            "арбуз",
            "вишня",
            "персик",
            "ананас",
            "персик",
            "арбуз",
            "слива",
            "картошка",
            "банан",
            "авокадо",
            "банан",
            "персик",
            "персик",
            "ананас",
            "персик",
            "арбуз"};

    public static void main(String[] args) {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 1);
        map.put("c", 2);
        map.put("d", 2);

        System.out.println(map.isEmpty());
    }


}

