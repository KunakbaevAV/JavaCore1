package lesson5;

/**
 * @autor Kunakbaev Artem
 */
public final class App {
    public static void main(String[] args) throws InterruptedException {
        //проверка через один поток
        StreamMono streamMono = new StreamMono();
        streamMono.doOperation();

        //проверка через два потока
        StreamDuo streamDuo = new StreamDuo();
        streamDuo.doOperation();
    }
}
