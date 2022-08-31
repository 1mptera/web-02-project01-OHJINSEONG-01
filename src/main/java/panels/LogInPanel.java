package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import models.User;
import utils.UsersLoader;

public class LogInPanel extends JPanel {

    private JPanel createIDPanel;
    private JTextField logInIDTextField;
    private JTextField logInPasswordTextField;
    private List<User> users;
    private JPanel contentPanel;
    private JPanel logInButtonPanel;
    private JLabel alertLabel;


    public LogInPanel(List<User> users, JPanel contentPanel, JPanel logInButtonPanel) {
        this.users = users;
        this.contentPanel = contentPanel;
        this.logInButtonPanel = logInButtonPanel;

        JPanel panel = new JPanel();
        this.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.add(panel, BorderLayout.PAGE_START);

        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(30, 30, 10, 30)));
        panel.setPreferredSize(new Dimension(600, 200));
        panel.setBackground(new Color(100, 0, 10, 50));
        panel.setLayout(new GridLayout(4, 1));

        panel.add(logInUserNameLabel());
        panel.add(logInIDTextField());
        panel.add(logInPasswordLabel());
        panel.add(logInPasswordTextField());

        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(0, 100, 10, 50));
        this.add(panel2);
        panel2.add(logInButton());
        panel2.add(createIDButton());

        JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        this.add(panel3, BorderLayout.SOUTH);
        panel3.add(alertLabel());
    }

    private JLabel logInUserNameLabel() {
        JLabel label = new JLabel("아이디");
        label.setForeground(Color.white);
        return label;
    }

    private JTextField logInIDTextField() {
        logInIDTextField = new JTextField(10);
        return logInIDTextField;
    }

    private JLabel logInPasswordLabel() {
        JLabel label = new JLabel("비밀번호");
        label.setForeground(Color.white);
        return label;
    }

    private JTextField logInPasswordTextField() {
        logInPasswordTextField = new JTextField(10);
        return logInPasswordTextField;
    }

    public JButton logInButton() {
        JButton button = new JButton("로그인");
        button.addActionListener(e -> {
            alertLabel.setText("아이디와 비밀번호를 다시 한번 확인해주세요!");

            for (User user : users) {
                if (notLogInError(user)) {
                    logInButtonPanel.removeAll();
                    this.removeAll();

                    user.logIn();

                    saveUsers();

                    logInButtonPanel.add(getUserAccountButton(user));

                    this.setVisible(false);
                    this.setVisible(true);
                    logInButtonPanel.setVisible(false);
                    logInButtonPanel.setVisible(true);
                }
            }
        });
        return button;
    }

    private void saveUsers() {
        UsersLoader usersLoader = new UsersLoader();
        try {
            usersLoader.save(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JButton getUserAccountButton(User user) {
        JButton button = new JButton(user.name());
        button.addActionListener(e -> {
            contentPanel.removeAll();

            JPanel userAccountPanel = new UserAccountPanel();
            contentPanel.add(userAccountPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }

    private boolean notLogInError(User user) {
        if (notExistUserName(user)) {
            return false;
        }
        if (notInputTextField()) {
            return false;
        }

        return true;
    }

    private boolean notExistUserName(User user) {
        int index = 0;
        if (logInIDTextField.getText().equals(user.userName())) {
            index = 1;
            if ((wrongPassword(user))) {
                return true;
            }
        }
        return index == 0;
    }

    private boolean wrongPassword(User user) {
        return !logInPasswordTextField.getText().equals(user.password());
    }

    private boolean notInputTextField() {
        return logInIDTextField.getText().replaceAll(" ", "").equals("")
                || logInPasswordTextField.getText().replaceAll(" ", "").equals("");
    }

    private JButton createIDButton() {
        JButton button = new JButton("회원 가입");
        button.addActionListener(e -> {
            this.removeAll();
            createIDPanel = new CreateIDPanel(users);

            this.add(createIDPanel);

            this.setVisible(false);
            this.setVisible(true);

        });
        return button;
    }

    private JLabel alertLabel() {
        alertLabel = new JLabel("");
        alertLabel.setForeground(Color.white);
        return alertLabel;
    }
}
