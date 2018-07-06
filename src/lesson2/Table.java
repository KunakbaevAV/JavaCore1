//Autor:
//Artem Kunakbaev

package lesson2;

class Table {
    private int[][] table;

    Table() {
        this.table = new int[4][4];
    }

    void setTable(String[] ... array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    table[i][j] = Integer.parseInt(array[i][j]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Массив вышел за пределы. Строка: " + i + ", столбец: " + j);
                } catch (NumberFormatException e){
                    System.out.println("Ячейка не является числом. Строка: " + i + ", столбец: " + j);
                }
            }
        }
    }
}
