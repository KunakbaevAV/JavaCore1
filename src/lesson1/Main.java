package lesson1;

public class Main {
    public static void main(String[] args) {
        Team sparta = new Team(
                "Спарта",
                "Мидас",
                "Зорро",
                "Одиссей",
                "Минотавр");

        Course course = new Course();

//        course.doAndShowResults(sparta);

        course.doIt(sparta);
        sparta.showResults();
    }
}
