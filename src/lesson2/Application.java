//Autor:
//Artem Kunakbaev

package lesson2;

public class Application {
    public static void main(String[] args) {
        Table table = new Table();

        table.setTable(
                new String[]{"1", "2", "3", "k", "5"},
                new String[]{"1", "t", "3", "4"},
                new String[]{"1", "2", "d", "4"},
                new String[]{"j", "j", "3", "4"},
                new String[]{"1", "2"});
    }
}
