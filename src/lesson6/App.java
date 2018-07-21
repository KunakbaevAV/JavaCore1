package lesson6;

/**
 * @autor Kunakbaev Artem
 */
public class App {
    public static void main(String[] args) {
        new Thread(Server::new).start();
        new Thread(Client::new).start();
    }
}

