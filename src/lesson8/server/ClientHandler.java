package lesson8.server;

import lesson8.ChatConstants;
import lesson8.server.service.BaseAuthService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @autor Kunakbaev Artem
 */
final class ClientHandler {
    private Server server;
    private Socket socket;
    private String nickName;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean authTimeOut = false;
    private boolean authOk = false;

    ClientHandler(final Server server, final Socket socket) {
        this.socket = socket;
        this.server = server;
        this.nickName = "";

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            final Thread messageHandlerThread = new Thread(this::handleMessages);
            messageHandlerThread.start();
            startTimeAuthorization();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании учетной записи");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    String getNickName() {
        return nickName;
    }

    private void handleMessages() {
        try {
            handleClientRequest();
            getMessages();
        } catch (SocketException | EOFException e) {
            System.out.println("Сессия была прервана");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.unsubscribe(this);
            server.broadcastMessage(nickName + " вышел из чата");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClientRequest() throws IOException {
        while (!authTimeOut) {
            final String message = in.readUTF();
            if (message.startsWith(ChatConstants.AUTHORIZE_FLAG)) {
                if (authorizeClient(message)) {
                    authOk = true;
                    break;
                }
            } else if (message.startsWith(ChatConstants.REGISTER_FLAG)) {
                if (registerClient(message)) {
                    authOk = true;
                    break;
                }
            }
        }
    }

    private boolean authorizeClient(final String message) {
        final int expectedTokenAmount = 3;
        final String[] authData = message.split("\\s");
        if (authData.length != expectedTokenAmount) {
            sendMessage("Пожалуйста, заполните все поля для авторизации");
            return false;
        }

        final String nickName = server.getAuthService().getNickByLoginPass(authData[1], authData[2]);
        if (nickName != null) {
            if (!server.isNickNameBusy(nickName)) {
                sendMessage(ChatConstants.AUTHORIZE_OK_FLAG + nickName);
                this.nickName = nickName;
                server.broadcastMessage(this.nickName + ChatConstants.CHAT_ENTERED_FLAG);
                server.subscribe(this);
                return true;
            } else sendMessage("Аккаут уже используется");
        } else sendMessage("Неправильный логин/пароль");
        return false;
    }

    private boolean registerClient(final String message) {
        final int expectedTokensAmount = 4;
        final String[] authData = message.split("\\s");
        if (authData.length != expectedTokensAmount) {
            sendMessage("Пожалуйста, заполните все поля для регистрации");
            return false;
        }

        final String login = authData[1];
        final String password = authData[2];
        final String nickName = authData[3];

        if (server.getAuthService() instanceof BaseAuthService) {
            final BaseAuthService authService = ((BaseAuthService) server.getAuthService());
            final boolean containsLogin = authService.containsLogin(login);
            final boolean containsNickName = authService.containsNickName(nickName);
            if (!containsLogin) {
                if (!containsNickName) {
                    authService.addEntry(login, password, nickName);
                    sendMessage(ChatConstants.REGISTER_OK_FLAG + nickName);
                    this.nickName = nickName;
                    server.broadcastMessage(this.nickName + ChatConstants.CHAT_ENTERED_FLAG);
                    server.subscribe(this);
                    return true;
                } else sendMessage("Такой ник уже используется");
            } else sendMessage("Этот логин занят");
        }
        return false;
    }

    String getCurrentDate() {
        final SimpleDateFormat messageDateFormat = new SimpleDateFormat("HH:mm");
        return "|" + messageDateFormat.format(new Date()) + "| ";
    }

    private void getMessages() throws IOException {
        while (true) {
            final String messageText = in.readUTF();
            System.out.println(ChatConstants.FROM + nickName + ": " + messageText);
            if (ChatConstants.TERMINATE_CONNECTION_FLAG.equals(messageText)) break;

            if (messageText.startsWith(ChatConstants.PRIVATE_MESSAGE_FLAG)) {
                final String[] parts = messageText.split("\\s");
                final String nickName = parts[1];
                final String message = messageText.substring(ChatConstants.PRIVATE_MESSAGE_FLAG.length()
                        + nickName.length() + 1);
                server.sendMessageToClient(nickName, this, message);
            } else server.broadcastMessage(getCurrentDate() + nickName + ": " + messageText);

            if (messageText.startsWith(ChatConstants.CLIENTS)) {
                server.sendClientList(this);
            }
        }
    }

    void sendMessage(final String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTimeAuthorization() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thread.sleep(120000);
        if (!authOk) {
            authTimeOut = true;
            try {
                socket.close();
                in.close();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Время для авторизации закончилось");
            }
        }
    }
}

