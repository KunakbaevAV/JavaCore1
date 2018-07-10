//Autor:
//Artem Kunakbaev

package lesson2;

public class MyException extends Exception {
    MyException(ArrayIndexOutOfBoundsException e){
        super("ошибка");
    }
}

