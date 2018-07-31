package lesson8.server.service;

/**
 * @autor Kunakbaev Artem
 */
public interface AuthService {

    void start();

    void stop();

    String getNickByLoginPass(String login, String password);
}

