package lesson7.client;

import lesson7.server.ClientHandler;
import lesson7.server.MyServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame {

    public static void main(String[] args) {
        new Client();
    }

    private JTextField chatField;
    private JTextArea chatArea;
    private final String SERVER_ADDR = "localhost";//127.0.0.1
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private AuthWindow authWindow;
//    private boolean closeClient = false;

    public Client() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);
            Thread t = new Thread(() -> {
                try {
                    while (true) {
//                        if (closeClient) break;
                        String str = in.readUTF();
                        if(str.startsWith("/authok")) {
                            setAuthorized(true);
                            break;
                        }
                        chatArea.append(str + "\n");
                    }
                    while (true) {
//                        if (closeClient) break;
                        String str = in.readUTF();
                        if (str.equals("/end")) {
                            break;
                        }
//                        if (str.startsWith("/w")){
//                            chatArea.append("privat");
//                        }
                        chatArea.append(str + "\n");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setAuthorized(false);
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBounds(200, 200, 500, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        authWindow = new AuthWindow(this);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(chatArea);
        add(jsp, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbAuth = new JButton("AUTH");
        JButton jbSend = new JButton("SEND");
        bottomPanel.add(jbSend, BorderLayout.EAST);
        bottomPanel.add(jbAuth, BorderLayout.WEST);
        chatField = new JTextField();
        bottomPanel.add(chatField, BorderLayout.CENTER);
        jbSend.addActionListener(e -> {
            if (!chatField.getText().trim().isEmpty()) {
                sendMsg();
                chatField.grabFocus();
            }
        });
        jbAuth.addActionListener(e -> authWindow.setVisible(true));
        chatField.addActionListener(e -> sendMsg());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
//                    closeClient = true;
                    out.writeUTF("/end");
                    out.flush();
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException exc) {
                }
            }
        });
        setVisible(true);
    }

    public void setAuthorized(boolean v) {
    }

    public void onAuthClick(String login, String pass) {
//        authWindow.setVisible(true);
        try {
//            String login = "login1";
//            String password = "pass1";
            out.writeUTF("/auth " + login + " " + pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(chatField.getText());
            chatField.setText("");
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }

    void sendPrivateMsg(String msg, ClientHandler client){
        MyServer.sendPrivateMessage(msg, client);
    }

}
