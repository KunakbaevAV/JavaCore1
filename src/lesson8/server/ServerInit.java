package lesson8.server;

/**
 * @autor Kunakbaev Artem
 */
public final class ServerInit {
    public static void main(String[] args) {
        final Server chatServer = new Server();
        chatServer.init(8189);
    }
}

