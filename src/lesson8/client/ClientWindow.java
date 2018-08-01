package lesson8.client;

import lesson8.ChatConstants;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @autor Kunakbaev Artem
 */
final class ClientWindow extends JFrame {
    private final static String DEFAULT_MESSAGE_TEXT = "Напишите свое сообщение...";

    private final JTextField fieldSendMessage = new JTextField();
    private final JTextArea areaViewMessage = new JTextArea();
    private final JButton buttonSendMessage = new JButton("Отправить");
    private final JPanel panelSendMessage = new JPanel(new BorderLayout());
    private final JScrollPane scrollViewMessage = new JScrollPane(areaViewMessage);

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private AuthWindow authWindow;

    void init(final String serverAdress, final int serverPort) {
        try {
            socket = new Socket(serverAdress, serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            startAuthorization();
        } catch (IOException e) {
            System.out.println("Соединение не установлено");
            System.exit(1);
        }

        setTitle("Клиент");
        setBounds(400, 100, 500, 500);
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addAreaViewMessage();
        addPanelSendMessage();
        addWindowListener(getWindowAdapter());

        final Thread threadMessageReader = new Thread(this::getMessages);
        threadMessageReader.setDaemon(true);
        threadMessageReader.start();
        setVisible(true);
    }

    private void startAuthorization() {
        authWindow = new AuthWindow(in, out);
        while (!authWindow.isAuthorized()) ;
        authWindow.setVisible(false);
    }

    private void addAreaViewMessage() {
        areaViewMessage.setEditable(false);
        areaViewMessage.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret) areaViewMessage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//        scrollViewMessage.add(areaViewMessage);
        add(scrollViewMessage, BorderLayout.CENTER);
    }

    private void addPanelSendMessage() {
        add(panelSendMessage, BorderLayout.SOUTH);
        panelSendMessage.add(buttonSendMessage, BorderLayout.EAST);
        panelSendMessage.add(fieldSendMessage, BorderLayout.CENTER);
        fieldSendMessage.addFocusListener(getFieldSendMessageFocusListener());
        fieldSendMessage.addActionListener(e -> sendMessage());
        fieldSendMessage.setText(DEFAULT_MESSAGE_TEXT);

        buttonSendMessage.addActionListener((e) -> {
            boolean hasEmptyMessage = fieldSendMessage.getText().trim().isEmpty();
            boolean hasDefaultMessage = fieldSendMessage.getText().equals(DEFAULT_MESSAGE_TEXT);
            if (!hasEmptyMessage && !hasDefaultMessage) {
                sendMessage();
                fieldSendMessage.grabFocus();
            }
        });
    }

    private WindowAdapter getWindowAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF(ChatConstants.TERMINATE_CONNECTION_FLAG);
                    out.flush();
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };
    }

    private void getMessages() {
        try {
            while (true) {
                final String message = in.readUTF();
                if (message.equalsIgnoreCase(ChatConstants.TERMINATE_CONNECTION_FLAG)) break;
                areaViewMessage.append(message + "\n");
            }
        } catch (Exception e) {
            System.out.println("Проблемы с соединением");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authWindow.setAuthorized(false);
        }
    }

    private void sendMessage() {
        try {
            out.writeUTF(fieldSendMessage.getText());
            out.flush();
            fieldSendMessage.setText("");
        } catch (IOException e) {
            System.out.println("Не получается отправить сообщение");
        }
    }

    private FocusListener getFieldSendMessageFocusListener() {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (DEFAULT_MESSAGE_TEXT.equals(fieldSendMessage.getText())) fieldSendMessage.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (fieldSendMessage.getText().isEmpty()) fieldSendMessage.setText(DEFAULT_MESSAGE_TEXT);
            }
        };
    }
}

