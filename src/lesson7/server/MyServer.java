package lesson7.server;

import lesson7.server.service.BaseAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    public static void main(String[] args) {
        new MyServer();
    }

    private ServerSocket server;
    private List<ClientHandler> clients;
    private BaseAuthService authService;

    BaseAuthService getAuthService() {
        return authService;
    }

    private final int PORT = 8189;

    public MyServer() {
        try {
            server = new ServerSocket(PORT);
            Socket socket;
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе сервера");
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.stop();
        }
    }

    synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }

    synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    synchronized void sendPrivateMessage(String name, String msg) {
        for (ClientHandler o : clients) {
            if (name.equals(o.getName())) o.sendMsg(msg);
        }
    }

    synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }

}