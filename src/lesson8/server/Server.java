package lesson8.server;

import lesson8.ChatConstants;
import lesson8.server.service.AuthService;
import lesson8.server.service.BaseAuthService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @autor Kunakbaev Artem
 */
class Server {
    private final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final AuthService authService = new BaseAuthService();

    void init(final int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            authService.start();
            Socket socket;
            while (true) { //search clients
                System.out.println("Сервер ожидает подключения...");
                socket = serverSocket.accept();
                System.out.println("Клиент подключился к серверу");
                new ClientHandler(this, socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            authService.stop();
        }
    }

    AuthService getAuthService() {
        return authService;
    }

    synchronized boolean isNIckNameBusy(final String nickName) {
        for (ClientHandler client : clients) {
            if (client.getNickName().equals(nickName)) return true;
        }
        return false;
    }

    synchronized void sendMessageToClient(final String toNickName, final ClientHandler from, final String message) {
        for (ClientHandler client : clients) {
            if (client.getNickName().equals(toNickName)) {
                from.sendMessage(from.getCurrentDate() + ChatConstants.PRIVATE_TO + client.getNickName() + ": " + message);
                client.sendMessage(from.getCurrentDate() + ChatConstants.PRIVATE_FROM + from.getNickName() + ": " + message);
                return;
            }
        }
        from.sendMessage(ChatConstants.NO_SUCH_USER_IN_ROOM);
    }

    synchronized void broadcastMessage(final String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    synchronized void unsubscribe(final ClientHandler client) {
        clients.remove(client);
    }

    synchronized void subscribe(final ClientHandler client) {
        clients.add(client);
    }
}

