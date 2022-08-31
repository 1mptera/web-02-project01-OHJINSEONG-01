package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import models.Comment;
import models.Post;
import models.User;
import utils.PostsLoader;

public class ModifyPostPanel extends JPanel {
    private Post post;
    private User user;
    private List<Post> posts;
    private JPanel contentPanel;
    private List<Comment> comments;
    private JTextArea textTextArea;
    private JTextField titleTextField;

    public ModifyPostPanel(Post post, User user, List<Post> posts, JPanel contentPanel, List<Comment> comments) {
        this.post = post;
        this.user = user;
        this.posts = posts;
        this.contentPanel = contentPanel;
        this.comments = comments;

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(new Color(100, 0, 0, 100));
        this.setLayout(new BorderLayout());

        titlePanel();

        textAreaPanel();

        buttonPanel();
    }

    private void titlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new GridLayout(3, 1));
        this.add(titlePanel, BorderLayout.PAGE_START);

        JLabel titleLabel = new JLabel("제목을 입력해주세요!");
        titleLabel.setForeground(Color.white);
        titlePanel.add(titleLabel);

        titleTextField = new JTextField(10);
        titlePanel.add(titleTextField);
        titleTextField.setText(post.title());

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

        textTextArea = new JTextArea(25,30);
        textTextArea.setLineWrap(true);
        textTextArea.setText(post.text());
        panel.add(textTextArea);
    }

    private void buttonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        JButton cancelButton = deleteButton();
        buttonPanel.add(cancelButton);

        if (user.id() == post.userId()) {
            JButton postButton = postButton();
            buttonPanel.add(postButton);
        }
    }

    private JButton deleteButton() {
        JButton cancelButton = new JButton("돌아가기");
        cancelButton.addActionListener(e -> {
            contentPanel.removeAll();

            PostPanel postPanel = new PostPanel(posts, post, contentPanel, user, comments);
            contentPanel.add(postPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return cancelButton;
    }

    private JButton postButton() {
        JButton button = new JButton("완료");
        button.addActionListener(e -> {
            contentPanel.removeAll();

            post.updateTitle(titleTextField.getText());
            post.updateText(textTextArea.getText());

            savePosts();

            PostPanel postPanel = new PostPanel(posts, post, contentPanel, user, comments);
            contentPanel.add(postPanel);

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
