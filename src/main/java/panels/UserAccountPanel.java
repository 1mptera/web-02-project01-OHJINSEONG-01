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
import models.Comment;
import models.Post;
import models.User;
import utils.UsersLoader;

public class UserAccountPanel extends JPanel {
    private User user;
    private List<User> users;
    private JPanel contentPanel;
    private JPanel logInButtonPanel;
    private List<Comment> comments;
    private List<Post> posts;

    public UserAccountPanel(User user, List<User> users, JPanel contentPanel, JPanel logInButtonPanel
            , List<Comment> comments, List<Post> posts) {
        this.user = user;
        this.users = users;
        this.contentPanel = contentPanel;
        this.logInButtonPanel = logInButtonPanel;
        this.comments = comments;
        this.posts = posts;

        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        initButtonPanel();

        initPersonalImformationPanel();
    }

    private void initButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        this.add(buttonPanel, BorderLayout.PAGE_START);

        buttonPanel.add(logOutButton());

        buttonPanel.add(deleteAccountButton());
    }

    private JButton logOutButton() {
        JButton button = new JButton("로그아웃");
        button.addActionListener(e -> {
            contentPanel.removeAll();
            logInButtonPanel.removeAll();

            user.logOut();
            logInButtonPanel.add(logInButton());

            saveUsers();

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }

    private JButton deleteAccountButton() {
        JButton button = new JButton("계정 삭제");
        button.addActionListener(e -> {
            contentPanel.removeAll();
            logInButtonPanel.removeAll();

            user.delete(comments, posts);

            logInButtonPanel.add(logInButton());

            saveUsers();

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }

    private void initPersonalImformationPanel() {
        JPanel imformationPanel = new JPanel();
        this.add(imformationPanel);
        imformationPanel.setPreferredSize(new Dimension(300, 400));
        imformationPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        imformationPanel.setBackground(new Color(100, 0, 0, 100));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(250, 300));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
                , BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        panel.setLayout(new GridLayout(5, 1));
        panel.setBackground(new Color(0, 100, 0, 100));
        imformationPanel.add(panel);

        JLabel name = new JLabel("이름 : " + user.name() + "님");
        name.setForeground(Color.white);
        panel.add(name);

        JLabel userName = new JLabel("닉네임 : " + user.userName());
        userName.setForeground(Color.white);
        panel.add(userName);

        JLabel gender = new JLabel("성별 : " + user.gender());
        gender.setForeground(Color.white);
        panel.add(gender);

        JLabel age = new JLabel("나이 : " + user.age() + "세");
        age.setForeground(Color.white);
        panel.add(age);

        JLabel birthDay = new JLabel("생년 월일: " + user.birthDay());
        birthDay.setForeground(Color.white);
        panel.add(birthDay);
    }

    private JButton logInButton() {
        JButton button = new JButton("로그인");
        button.addActionListener(e -> {
            contentPanel.removeAll();

            JPanel logInPanel = new LogInPanel(users, contentPanel, logInButtonPanel, comments, posts);
            contentPanel.add(logInPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
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
}
