package lesson3.task2;

import java.util.HashMap;
import java.util.HashSet;

/*
 *@autor Kunakbaev Artem
 */
public class PhoneDirectory {
    private HashMap<String, HashSet<String>> dir = new HashMap<>();

    public void add(String name, String number) {
        if (!dir.containsKey(name)) dir.put(name, new HashSet<>());

        dir.get(name).add(number);
    }

    public void get(String name) {
        if (dir.containsKey(name)) {
            System.out.println("Телефон(ы) пользователя с фамилией " + name + " " + dir.get(name));
        } else {
            System.out.println("Нет телефонов пользователя с фамилией " + name);
        }
    }

    public void showDirectory() {
        System.out.println("Телефонный справочник: ");
        System.out.println(dir);
    }
}
