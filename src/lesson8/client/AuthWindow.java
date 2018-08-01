package lesson8.client;

import lesson8.ChatConstants;

import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;

/**
 * @autor Kunakbaev Artem
 */
final class AuthWindow extends JFrame {
    private final JLabel labelInfo = new JLabel("Авторизация");
    private final JLabel labelLogin = new JLabel("Логин: ");
    private final JLabel labelPass = new JLabel("Пароль: ");
    private final JLabel labelNick = new JLabel("Ник: ");
    private final JTextField fieldLogin = new JTextField();
    private final JTextField fieldNick = new JTextField();
    private final JPasswordField fieldPass = new JPasswordField();
    private final JPanel panelNick = new JPanel();
    private final JPanel panelLogin = new JPanel();
    private final JPanel panelPass = new JPanel();
    private final JPanel panelButton = new JPanel();
    private final JButton buttonOk = new JButton("Ok");
    private final JButton buttonCancel = new JButton("Exit");
    private final JCheckBox checkBox = new JCheckBox("Новый клиент");
    private final Dimension buttonSize = new Dimension(100, 30);
    private volatile boolean isAuthorized = false;

    AuthWindow(DataInputStream in, DataOutputStream out) {
        setTitle("Авторизация");
        setBounds(500, 200, 250, 250);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final GridLayout layoutMain = new GridLayout(6, 1);
        setLayout(layoutMain);

        setUpInfoPanel();
        setUpAuthPanel();
        setUpButtonPanel(in, out);

        add(labelInfo);
        add(checkBox);
        add(panelNick);
        add(panelLogin);
        add(panelPass);
        add(panelButton);

        setVisible(true);
    }

    private void setUpInfoPanel() {
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        labelInfo.setVerticalAlignment(SwingConstants.BOTTOM);
        checkBox.setHorizontalAlignment(SwingConstants.RIGHT);
        checkBox.addChangeListener((e) -> {
            if (!checkBox.isSelected()) fieldNick.setEditable(false);
            else fieldNick.setEditable(true);
        });
    }

    private void setUpAuthPanel() {
        panelNick.setLayout(new GridLayout(1, 2));
        labelNick.setHorizontalAlignment(JLabel.RIGHT);
        fieldNick.setEditable(false);
        panelNick.add(labelNick);
        panelNick.add(fieldNick);

        panelLogin.setLayout(new GridLayout(1, 2));
        labelLogin.setHorizontalAlignment(JLabel.RIGHT);
        panelLogin.add(labelLogin);
        panelLogin.add(fieldLogin);

        panelPass.setLayout(new GridLayout(1, 2));
        labelPass.setHorizontalAlignment(JLabel.RIGHT);
        panelPass.add(labelPass);
        panelPass.add(fieldPass);
    }

    private void setUpButtonPanel(final DataInputStream in, final DataOutputStream out) {
        buttonCancel.setPreferredSize(buttonSize);
        buttonCancel.addActionListener((e) -> System.exit(0));
        buttonOk.setPreferredSize(buttonSize);
        buttonOk.addActionListener((e) -> new Thread(() -> onOkClick(in, out)).start());
        panelButton.add(buttonCancel);
        panelButton.add(buttonOk);
    }

    private void onOkClick(DataInputStream in, DataOutputStream out) {
        buttonOk.setEnabled(false);
        checkBox.setEnabled(false);
        if (checkBox.isSelected()) {
            labelInfo.setText("Регистрация...");
            tryAuthorize(in, out, ChatConstants.REGISTER_FLAG, ChatConstants.REGISTER_OK_FLAG);
        } else {
            labelInfo.setText("Авторизация...");
            tryAuthorize(in, out, ChatConstants.AUTHORIZE_FLAG, ChatConstants.AUTHORIZE_OK_FLAG);
        }
    }

    private void tryAuthorize(final DataInputStream in, final DataOutputStream out, final String authorizeFlag, final String authorizeOkFlag) {
        try {
            out.writeUTF(authorizeFlag + fieldLogin.getText()
                    + " " + String.valueOf(fieldPass.getPassword())
                    + " " + fieldNick.getText());
            out.flush();
            clearFields();
            while (true) {
                final String infoMessage = in.readUTF();
                if (infoMessage.startsWith(authorizeOkFlag)) {
                    setAuthorized(true);
                    break;
                }
                labelInfo.setText(infoMessage);
                buttonOk.setEnabled(true);
                checkBox.setEnabled(true);
            }
        } catch (ConnectException e) {
            labelInfo.setText("Проблемы с соединением");
            buttonOk.setEnabled(true);
            checkBox.setEnabled(true);
        } catch (IOException e) {
            labelInfo.setText("Вышло время для авторизации");
            buttonOk.setEnabled(true);
            checkBox.setEnabled(true);
        }
    }

    void setAuthorized(final boolean b) {
        isAuthorized = b;
    }

    boolean isAuthorized() {
        return isAuthorized;
    }

    private void clearFields() {
        fieldNick.setText("");
        fieldLogin.setText("");
        fieldPass.setText("");
    }
}


