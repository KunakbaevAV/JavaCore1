package lesson5;

/**
 * @autor Kunakbaev Artem
 */
public class App {
    public static void main(String[] args) {
        StreamMono streamMono = new StreamMono();
        streamMono.doOperation();

        StreamDuo streamDuo = new StreamDuo();
        streamDuo.doOperation();
    }
}
