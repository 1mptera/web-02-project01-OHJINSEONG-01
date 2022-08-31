package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import models.Post;
import models.User;

public class PostPanel extends JPanel {
    private List<Post> posts;
    private Post post;
    private JPanel contentPanel;
    private User user;
    private JTextArea textTextArea;
    private JTextField titleTextField;

    public PostPanel(List<Post> posts, Post post, JPanel contentPanel, User user) {
        this.posts = posts;
        this.post = post;
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
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new GridLayout(3, 1));
        this.add(titlePanel, BorderLayout.PAGE_START);

        titleTextField = new JTextField(10);
        titlePanel.add(titleTextField);
        titleTextField.setEnabled(false);
        titleTextField.setText(post.title());
    }

    private void textAreaPanel() {
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setOpaque(false);
        textAreaPanel.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        textAreaPanel.add(panel);
        this.add(textAreaPanel);

        textTextArea = new JTextArea(25, 30);
        textTextArea.setText(post.text());
        textTextArea.setEnabled(false);
        panel.add(textTextArea);
    }

    private void buttonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        JButton cancelButton = deleteButton();
        buttonPanel.add(cancelButton);

        if(user.id() == post.userId()) {
            JButton postButton = postButton();
            buttonPanel.add(postButton);
        }
    }

    private JButton deleteButton() {
        JButton cancelButton = new JButton("돌아가기");
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
        JButton button = new JButton("수정");
        button.addActionListener(e -> {
            contentPanel.removeAll();


            SharePostPanel sharePostPanel = new SharePostPanel(posts, contentPanel, user);
            contentPanel.add(sharePostPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }
}
