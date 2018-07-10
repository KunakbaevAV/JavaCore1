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

        phoneDirectory.writePhoneNumber("Vasia", "89998888888");
        phoneDirectory.writePhoneNumber("Vasia", "89998333333");
        phoneDirectory.writePhoneNumber("Fedia", "89998885555");
        phoneDirectory.writePhoneNumber("Maria", "89998666666");
        phoneDirectory.writePhoneNumber("Maria", "89998666666");

        phoneDirectory.showDirectory();
    }
}

