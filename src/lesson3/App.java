package lesson3;

import lesson3.task1.UniArray;
import lesson3.task2.PhoneDirectory;

/*
 *@autor Kunakbaev Artem
 */
public class App {
    public static void main(String[] args) {

        // проверяем первое задание:
        String[] words = new String[]{
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

        UniArray uniWords = new UniArray(words);
        uniWords.showArray();

        //проверяем второе задание:
        PhoneDirectory phoneDirectory = new PhoneDirectory();

        phoneDirectory.add("Иванов", "89998888888");
        phoneDirectory.add("Иванов", "89998333333");
        phoneDirectory.add("Петров", "89998885555");
        phoneDirectory.add("Сидоров", "89998666666");
        phoneDirectory.add("Сидоров", "89998666666");

        phoneDirectory.get("Иванов");

        phoneDirectory.showDirectory();
    }
}

