package lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @autor Kunakbaev Artem
 */
class Server {
    private ServerSocket server = null;
    private Socket socket = null;

    Server() {
        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");
            socket = server.accept();
            System.out.println("Клиент подключился");
            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            new Thread(() -> doInputChat(sc)).start();
            new Thread(() -> doOutputChat(pw)).start();
        } catch (IOException e) {
            System.out.println("Ошибка инициализации сервера");
        } finally {
            try {
                assert server != null;
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doInputChat(Scanner sc) {
        String string;
        while (true) {
            string = sc.nextLine();
            if (string.equals("end")) break;
            System.out.println(string);
        }
    }

    private void doOutputChat(PrintWriter pw) {
        String string;
        Scanner sc = new Scanner(System.in);
        while (true) {
            string = sc.nextLine();
            if (string.equals("end")) break;
            pw.println(string);
            pw.flush();
        }
    }
}

