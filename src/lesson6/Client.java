package lesson6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @autor Kunakbaev Artem
 */
class Client extends JFrame {
    private final JTextField chatField = new JTextField();
    private final JTextArea chatArea = new JTextArea();
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private boolean end;

    Client() throws HeadlessException {
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Проблемы с подключением к серверу");
        }
        setBounds(400, 300, 300, 200);
        setTitle("Мой чатик");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chatArea.setFocusable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        final JScrollPane jsp = new JScrollPane(chatArea);
        add(jsp, BorderLayout.CENTER);
        end = false;
        chatField.addActionListener(e -> enterMessage(chatField));
        final JPanel botPane = new JPanel(new BorderLayout());
        botPane.add(chatField, BorderLayout.CENTER);
        final JButton btnEnter = new JButton("Enter");
        botPane.add(btnEnter, BorderLayout.EAST);
        btnEnter.addActionListener(e -> enterMessage(chatField));
        add(botPane, BorderLayout.SOUTH);

        new Thread(() -> doInputChatClient(in)).start();
        new Thread(() -> doOutputChatClient(out)).start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    super.windowClosing(e);
                    end = true;
                    out.println("end");
                    out.flush();
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    private void enterMessage(JTextField textField) {
        chatArea.append(enterDate() + textField.getText() + "\n");
        doOutputChatClient(out);
        textField.setText("");
        textField.requestFocus();
    }

    private String enterDate() {
        final Date date = new Date();
        SimpleDateFormat formatEnterDate = new SimpleDateFormat("HH:mm:ss: ");
        return formatEnterDate.format(date);
    }

    private void doInputChatClient(Scanner sc) {
        String string;
        while (!end) {
            try {
                string = sc.nextLine();
                if (string.equals("end")) break;
                chatArea.append(enterDate() + string + "\n");
                chatArea.grabFocus();
            } catch (Exception e) {
                System.out.println("Чат закрылся");
            }
        }
    }

    private void doOutputChatClient(PrintWriter pw) {
        String string;
        string = chatField.getText();
        pw.println(enterDate() + string);
        pw.flush();
        chatArea.grabFocus();
    }
}

