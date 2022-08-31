package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import models.Post;
import models.User;
import utils.PostsLoader;

public class CreatePostPanel extends JPanel {
    private List<Post> posts;
    private JPanel contentPanel;
    private User user;
    private JTextArea textTextArea;
    private JTextField titleTextField;

    public CreatePostPanel( List<Post> posts, JPanel contentPanel, User user) {
        this.posts = posts;
        this.contentPanel = contentPanel;
        this.user = user;
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(new Color(100, 0, 0, 100));
        this.setLayout(new BorderLayout());

        titlePanel();

        textAreaPanel();

        buttonPanel();
    }

    private void titlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(3, 1));
        titlePanel.setOpaque(false);
        this.add(titlePanel, BorderLayout.PAGE_START);

        JLabel titleLabel = new JLabel("제목을 입력해주세요!");
        titleLabel.setForeground(Color.white);
        titlePanel.add(titleLabel);

        titleTextField = new JTextField(10);
        titlePanel.add(titleTextField);

        JLabel textAreaLabel = new JLabel("내용을 입력해주세요!");
        textAreaLabel.setForeground(Color.white);
        titlePanel.add(textAreaLabel);
    }

    private void textAreaPanel() {
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setOpaque(false);
        textAreaPanel.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        textAreaPanel.add(panel);
        this.add(textAreaPanel);

        textTextArea = new JTextArea(25, 30);
        panel.add(textTextArea);
    }

    private void buttonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        JButton cancelButton = deleteButton();
        buttonPanel.add(cancelButton);

        JButton postButton = postButton();
        buttonPanel.add(postButton);
    }

    private JButton deleteButton() {
        JButton cancelButton = new JButton("취소");
        cancelButton.addActionListener(e -> {
            contentPanel.removeAll();

            SharePostPanel sharePostPanel = new SharePostPanel(posts, contentPanel, user);
            contentPanel.add(sharePostPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return cancelButton;
    }

    private JButton postButton() {
        JButton button = new JButton("등록");
        button.addActionListener(e -> {
            contentPanel.removeAll();

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm");
            String time = now.format(dateTimeFormatter);

            Post post = new Post(titleTextField.getText(), user.userName(), textTextArea.getText()
                    , "CREATED", 0, 0, user.id(), time);
            posts.add(post);

            savePosts();

            SharePostPanel sharePostPanel = new SharePostPanel(posts, contentPanel, user);
            contentPanel.add(sharePostPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }

    private void savePosts() {
        PostsLoader postsLoader = new PostsLoader();
        try {
            postsLoader.save(posts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
