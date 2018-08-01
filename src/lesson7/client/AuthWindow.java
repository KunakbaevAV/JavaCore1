package lesson7.client;

import javax.swing.*;
import java.awt.*;

/**
 * @autor Kunakbaev Artem
 */
class AuthWindow extends JFrame {
    private String login;
    private String pass;
    private JTextField fieldLogin;
    private JTextField fieldPass;

    AuthWindow(Client client) {
        setBounds(300, 300, 300, 150);
        setTitle("Авторизация");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panelAuth = new JPanel(new GridLayout(4, 1));
        fieldLogin = new JTextField("login1");
        fieldPass = new JTextField("pass1");
        JLabel labelLogin = new JLabel("Name:");
        JLabel labelPass = new JLabel("Password:");
        panelAuth.add(labelLogin);
        panelAuth.add(fieldLogin);
        panelAuth.add(labelPass);
        panelAuth.add(fieldPass);
        add(panelAuth, BorderLayout.CENTER);
        JButton btnOk = new JButton("Ok");
        btnOk.addActionListener(e -> {
            login = fieldLogin.getText();
            pass = fieldPass.getText();
            sendNameAndPass(client);
            client.setTitle(login);
            setVisible(false);
        });
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> setVisible(false));

        JPanel panelBottom = new JPanel(new GridLayout(1, 2));
        panelBottom.add(btnCancel);
        panelBottom.add(btnOk);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void sendNameAndPass(Client client) {
        client.onAuthClick(login, pass);
    }
}

