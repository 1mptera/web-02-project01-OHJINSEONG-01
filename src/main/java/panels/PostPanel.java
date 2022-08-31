package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import models.Comment;
import models.Post;
import models.User;
import utils.CommentsLoader;
import utils.PostsLoader;

public class PostPanel extends JPanel {
    private List<Post> posts;
    private Post post;
    private JPanel contentPanel;
    private User user;
    private List<Comment> comments;
    private JTextArea textTextArea;
    private JTextField titleTextField;
    private GridBagConstraints constraint;
    private GridBagLayout gbl;
    private JTextField commentTextField;
    private JPanel textAreaPanel;
    private JTextArea inputCommentTextArea;
    private JLabel titleLabel;

    public PostPanel(List<Post> posts, Post post, JPanel contentPanel, User user, List<Comment> comments) {
        this.posts = posts;
        this.post = post;
        this.contentPanel = contentPanel;
        this.user = user;
        this.comments = comments;

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setPreferredSize(new Dimension(400, 600));
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

        titleLabel = new JLabel("제목                           추천수: " + post.likeCount() + "  조회수: "
                + post.viewCount() + "  작성자 : " + post.userName());
        titleLabel.setForeground(Color.white);
        titlePanel.add(titleLabel);

        titleTextField = new JTextField(10);
        titlePanel.add(titleTextField);
        titleTextField.setEnabled(false);
        titleTextField.setText(post.title());

        JLabel textAreaLabel = new JLabel("내용");
        textAreaLabel.setForeground(Color.white);
        titlePanel.add(textAreaLabel);
    }

    private void textAreaPanel() {
        textAreaPanel = new JPanel();
        textAreaPanel.setPreferredSize(new Dimension(400, 600));
        textAreaPanel.setOpaque(false);
        textAreaPanel.setLayout(new BorderLayout());
        this.add(textAreaPanel);

        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JTextArea textTextArea = new JTextArea(25, 30);
        textTextArea.setEnabled(false);
        textTextArea.setLineWrap(true);
        textTextArea.setText(post.text());

        panel.add(textTextArea);
        textAreaPanel.add(scrollPane, BorderLayout.PAGE_START);

        initCommentListPanel();

        initinputCommentTextAreaPanel();
    }

    private void initCommentListPanel() {
        textAreaPanel.add(new JLabel("댓글 창"));

        int i = 0;

        constraint = new GridBagConstraints();
        gbl = new GridBagLayout();

        JPanel jpList = new JPanel(gbl);

        JScrollPane scrollSingle = new JScrollPane(jpList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollSingle.setPreferredSize(new Dimension(400, 150));

        for (Comment comment : comments) {
            if (comment.postId() == post.id()) {
                if (!comment.status().equals("DELETED")) {
                    JPanel addCommentPanel = new JPanel();
                    addCommentPanel.setLayout(new BorderLayout());

                    addCommentPanel.setPreferredSize(new Dimension(330, 40));
                    addCommentPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3)
                            , BorderFactory.createLineBorder(Color.BLACK)));

                    JLabel userNameLabel = new JLabel(comment.userName() + ": ");
                    addCommentPanel.add(userNameLabel, BorderLayout.WEST);

                    addCommentPanel.add(new JLabel(comment.text()));

                    if (comment.userId() == user.id()) {
                        addCommentPanel.add(deleteCommentButton(comment), BorderLayout.EAST);
                    }

                    gbAdd(addCommentPanel, 0, i, 1, 1, 1, 1, jpList);
                    i += 1;
                }
            }
        }

        textAreaPanel.add(scrollSingle);
    }

    public void gbAdd(Component c, int x, int y, int w, int h, int k, int t, JPanel jpanel) {
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = w;
        constraint.gridheight = h;

        constraint.weightx = k;
        constraint.weighty = t;

        gbl.setConstraints(c, constraint);

        jpanel.add(c);
    }

    private void initinputCommentTextAreaPanel() {
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setOpaque(false);
        textAreaPanel.add(textFieldPanel, BorderLayout.SOUTH);
        textFieldPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(280, 65));

        inputCommentTextArea = new JTextArea(3, 21);
        inputCommentTextArea.setLineWrap(true);

        panel.add(inputCommentTextArea);

        textFieldPanel.add(scrollPane, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        buttonPanel.add(inputCommentTextField());

        textFieldPanel.add(buttonPanel, BorderLayout.EAST);
    }

    private JButton inputCommentTextField() {
        JButton button = new JButton("등록");
        button.addActionListener(e -> {
            contentPanel.removeAll();

            Comment comment = new Comment(inputCommentTextArea.getText(), user.userName(), post.id(), user.id(), "CREATED");
            comments.add(comment);

            saveComments();

            PostPanel postPanel = new PostPanel(posts, post, contentPanel, user, comments);
            contentPanel.add(postPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }

    private void saveComments() {
        CommentsLoader commentsLoader = new CommentsLoader();
        try {
            commentsLoader.save(comments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JButton deleteCommentButton(Comment comment) {
        JButton button = new JButton("삭제");
        button.addActionListener(e -> {
            contentPanel.removeAll();

            comment.delete();
            saveComments();

            PostPanel postPanel = new PostPanel(posts, post, contentPanel, user, comments);
            contentPanel.add(postPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }

    private void buttonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.add(goSharePostPanelButton());

        buttonPanel.add(likeButton());

        if (user.id() == post.userId()) {
            JButton modifyButton = modifyButton();
            buttonPanel.add(modifyButton);

            JButton cancelButton = deleteButton();
            buttonPanel.add(cancelButton);
        }
    }

    private JButton likeButton() {
        JButton button = new JButton("추천");
        button.addActionListener(e -> {
            post.addLikeCount();

            savePosts();

            titleLabel.setText("제목                           추천수: " + post.likeCount() + "  조회수: "
                    + post.viewCount() + "  작성자 : " + post.userName());

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton goSharePostPanelButton() {
        JButton cancelButton = new JButton("돌아가기");
        cancelButton.addActionListener(e -> {
            contentPanel.removeAll();

            SharePostPanel sharePostPanel = new SharePostPanel(posts, contentPanel, user, comments);
            contentPanel.add(sharePostPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return cancelButton;
    }

    private JButton modifyButton() {
        JButton button = new JButton("수정");
        button.addActionListener(e -> {
            contentPanel.removeAll();

            ModifyPostPanel modifyPostPanel = new ModifyPostPanel(post, user, posts, contentPanel, comments);
            contentPanel.add(modifyPostPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }

    private JButton deleteButton() {
        JButton button = new JButton("삭제");
        button.addActionListener(e -> {
            contentPanel.removeAll();

            post.deleted();
            savePosts();

            SharePostPanel sharePostPanel = new SharePostPanel(posts, contentPanel, user, comments);
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
