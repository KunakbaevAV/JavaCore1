package lesson8.client;

/**
 * @autor Kunakbaev Artem
 */
public final class ClientInit {
    public static void main(String[] args) {
        ClientWindow client = new ClientWindow();
        client.init("localhost", 8189);
    }
}

