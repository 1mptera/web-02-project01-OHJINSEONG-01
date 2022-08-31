package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import models.User;
import utils.UsersLoader;

public class CreateIDPanel extends JPanel {
    private JTextField createUserNameTextField;
    private JTextField createPasswordTextField;
    private JTextField reconfirmPasswordTextField;
    private JTextField inputNameTextField;
    private JTextField inputIndentifyNumberTextField;
    private List<User> users;
    private JCheckBox selectManCheckBox;
    private JCheckBox selectWomanCheckBox;
    private JLabel alertLabel;
    private String selectedGender = "";
    private int creatId;

    public CreateIDPanel(List<User> users) {
        this.users = users;

        JPanel panel = new JPanel();

        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.add(panel, BorderLayout.PAGE_START);

        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(10, 100, 10, 100)));
        panel.setPreferredSize(new Dimension(600, 200));
        panel.setBackground(new Color(100, 0, 10, 50));
        panel.setLayout(new GridLayout(6, 1));

        panel.add(userNameLabel());
        panel.add(createUserNameTextField());
        panel.add(passwordLabel());
        panel.add(createPasswordTextField());
        panel.add(reconfirmPasswordLabel());
        panel.add(reconfirmPasswordTextField());

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(600, 200));
        panel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(20, 100, 20, 100)));
        panel2.setLayout(new GridLayout(5, 1));
        panel2.setBackground(new Color(0, 100, 10, 50));
        this.add(panel2);

        panel2.add(nameLabel());
        panel2.add(inputNameTextField());
        panel2.add(indentifyNumberLabel());
        panel2.add(inputIndentifyNumberTextField());

        JPanel checkBoxPanel = new JPanel();

        checkBoxPanel.setBackground(new Color(0, 100, 10, 50));
        panel2.add(checkBoxPanel);
        checkBoxPanel.add(selectManCheckBox());
        checkBoxPanel.add(selectManButton());
        checkBoxPanel.add(selectWomanCheckBox());
        checkBoxPanel.add(selectWomanButton());

        JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        this.add(panel3, BorderLayout.SOUTH);
        panel3.add(alertLabel());
        panel3.add(createIDButton());
    }

    private JLabel userNameLabel() {
        JLabel label = new JLabel("아이디");
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createUserNameTextField() {
        createUserNameTextField = new JTextField(10);
        return createUserNameTextField;
    }

    private JLabel passwordLabel() {
        JLabel label = new JLabel("비밀번호");
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createPasswordTextField() {
        createPasswordTextField = new JTextField(10);
        return createPasswordTextField;
    }

    private JLabel reconfirmPasswordLabel() {
        JLabel label = new JLabel("비밀번호 재확인");
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField reconfirmPasswordTextField() {
        reconfirmPasswordTextField = new JTextField(10);
        return reconfirmPasswordTextField;
    }

    private JLabel nameLabel() {
        JLabel label = new JLabel("이름");
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField inputNameTextField() {
        inputNameTextField = new JTextField(10);
        return inputNameTextField;
    }

    private JLabel indentifyNumberLabel() {
        JLabel label = new JLabel("주민등록번호   ex) 19950828-19xxxxx");
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField inputIndentifyNumberTextField() {
        inputIndentifyNumberTextField = new JTextField(10);
        inputIndentifyNumberTextField.setText("");
        return inputIndentifyNumberTextField;
    }

    private JButton selectManButton() {
        JButton selectManButton = new JButton("남자");
        selectManButton.addActionListener(e -> {
            selectManCheckBox.doClick();
        });
        return selectManButton;
    }

    private JCheckBox selectManCheckBox() {
        selectManCheckBox = new JCheckBox();
        selectManCheckBox.addActionListener(e -> {
            selectWomanCheckBox.setSelected(false);
            selectManCheckBox.setSelected(true);

            selectedGender = "남자";
        });
        return selectManCheckBox;
    }

    private JButton selectWomanButton() {
        JButton button = new JButton("여자");
        button.addActionListener(e -> {
            selectWomanCheckBox.doClick();
        });
        return button;
    }

    private JCheckBox selectWomanCheckBox() {
        selectWomanCheckBox = new JCheckBox();
        selectWomanCheckBox.addActionListener(e -> {
            selectManCheckBox.setSelected(false);
            selectWomanCheckBox.setSelected(true);

            selectedGender = "여자";
        });
        return selectWomanCheckBox;
    }

    private JLabel alertLabel() {
        alertLabel = new JLabel("");
        alertLabel.setForeground(Color.white);
        return alertLabel;
    }

    private JButton createIDButton() {
        JButton button = new JButton("가입하기");
        button.addActionListener(e -> {
            if (reconfirmPasswordError()) {
                alertLabel.setText("비밀번호가 틀렸습니다. 다시 한번 입력해주세요.");
            }
            if (unSelectedGender()) {
                alertLabel.setText("성별을 골라주세요.");
            }
            if (notInputAll()) {
                alertLabel.setText("전부 기입 해주세요.");
            }

            if (notInputError()) {
                this.removeAll();

                creatId();

                User user = new User(inputNameTextField.getText(), inputIndentifyNumberTextField.getText(), selectedGender
                        , createUserNameTextField.getText(), createPasswordTextField.getText(), "", creatId);
                users.add(user);

                saveUsers();

                JFrame frame = new JFrame();
                frame.setSize(250, 100);
                frame.add(new JLabel("     회원가입이 완료되었습니다!"));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

                this.setVisible(false);
                this.setVisible(true);
            }
        });
        return button;
    }

    private void creatId() {
        UsersLoader usersLoader = new UsersLoader();
        try {
            creatId = usersLoader.createId();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUsers() {
        UsersLoader usersLoader = new UsersLoader();
        try {
            usersLoader.save(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean notInputError() {
        if (reconfirmPasswordError()) {
            return false;
        }

        if (notInputAll()) {
            return false;
        }

        if (unSelectedGender()) {
            return false;
        }

        return true;
    }

    private boolean reconfirmPasswordError() {
        return !createPasswordTextField.getText().equals(reconfirmPasswordTextField.getText());
    }

    private boolean notInputAll() {
        return inputIndentifyNumberTextField.getText().replaceAll(" ", "").equals("")
                || inputNameTextField.getText().replaceAll(" ", "").equals("")
                || createUserNameTextField.getText().replaceAll(" ", "").equals("")
                || createPasswordTextField.getText().replaceAll(" ", "").equals("");
    }

    private boolean unSelectedGender() {
        return selectedGender.equals("");
    }

    private boolean overLapUserName() {
        for (User user : users) {
            if (createUserNameTextField.getText().equals(user.userName())) {
                return true;
            }
        }
        return false;
    }
}
