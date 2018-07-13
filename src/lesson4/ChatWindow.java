package lesson4;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @autor Kunakbaev Artem
 */
class ChatWindow extends JFrame {
    private JTextArea chatAll = new JTextArea();

    ChatWindow() throws HeadlessException {
        setTitle("Мой чатик");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(400, 300, 300, 200);

        chatAll.setFocusable(false);
        JScrollPane jsp = new JScrollPane(chatAll);
        add(jsp, BorderLayout.CENTER);

        JPanel botPane = new JPanel(new BorderLayout());
        JTextField chatInput = new JTextField();
        chatInput.addActionListener(e -> enterMessage(chatInput));
        JButton btnEnter = new JButton("Enter");
        btnEnter.addActionListener(e -> enterMessage(chatInput));
        botPane.add(chatInput, BorderLayout.CENTER);
        botPane.add(btnEnter, BorderLayout.EAST);
        add(botPane, BorderLayout.SOUTH);
    }

    private void enterMessage(JTextField textField) {
        chatAll.append(enterDate() + " " + textField.getText() + "\n");
        textField.setText("");
        textField.requestFocus();
    }

    private String enterDate() {
        Date date = new Date();
        SimpleDateFormat formatEnterDate = new SimpleDateFormat("HH:mm:ss: ");
        return formatEnterDate.format(date);
    }
}