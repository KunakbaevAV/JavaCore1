package lesson7;

import lesson7.client.Client;
import lesson7.server.MyServer;

import java.util.Scanner;

/**
 * @autor Kunakbaev Artem
 */
public class App {

    public static void main(String[] args) {
        new Thread(MyServer::new);
//        new MyServer();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        new Client();
    }
}

