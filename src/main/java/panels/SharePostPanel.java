package panels;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import models.Comment;
import models.Post;
import models.User;

public class SharePostPanel extends JPanel {
    private final List<Post> undeletedPosts;
    private List<Post> posts;
    private JPanel contentPanel;
    private User user;
    private List<Comment> comments;
    private JPanel displayPanel;

    public SharePostPanel(List<Post> posts, JPanel contentPanel, User user, List<Comment> comments) {
        this.posts = posts;
        this.contentPanel = contentPanel;
        this.user = user;
        this.comments = comments;

        undeletedPosts = new ArrayList<>();

        for (Post post : posts) {
            if (!post.status().equals(Post.DELETED)) {
                undeletedPosts.add(post);
            }
        }
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        initSharePostPanel();
    }

    public void initSharePostPanel() {
        JPanel sharePostPanel = new JPanel();
        sharePostPanel.setOpaque(false);
        this.add(sharePostPanel, BorderLayout.PAGE_START);

        String[] words = {Post.USERNAME, Post.TITLE, Post.TEXT};
        JComboBox comboBox = new JComboBox(words);
        sharePostPanel.add(comboBox);

        JTextField searchField = new JTextField(15);
        sharePostPanel.add(searchField);

        JButton searchButton = new JButton("확인");
        searchButton.addActionListener(event -> {
            String text = searchField.getText();

            if (text.isBlank()) {
                displayPanel.removeAll();

                PostListPanel postListPanel = null;
                try {
                    postListPanel = new PostListPanel(displayPanel, contentPanel, undeletedPosts, user, comments);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                displayPanel.add(postListPanel);

                displayPanel.setVisible(false);
                displayPanel.setVisible(true);
            }

            if (!text.isBlank()) {
                String selection = String.valueOf(comboBox.getSelectedItem());

                if (selection.equals(Post.USERNAME)) {
                    try {
                        searchPostsUserNameKeyword(undeletedPosts, text);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (selection.equals(Post.TITLE)) {
                    searchPostsTitleKeyword(undeletedPosts, text);
                }

                if (selection.equals(Post.TEXT)) {
                    searchPostsTextKeyword(undeletedPosts, text);
                }
            }
        });
        sharePostPanel.add(searchButton);

        sharePostPanel.add(createPostButton());

        initPostListPanel();
    }

    private JButton createPostButton() {
        JButton button = new JButton("글쓰기");
        button.addActionListener(e -> {
            this.removeAll();

            CreatePostPanel createPostPanel = new CreatePostPanel(undeletedPosts, contentPanel, user,comments);
            contentPanel.add(createPostPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private void initPostListPanel() {
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setOpaque(false);
        this.add(displayPanel);

        PostListPanel postListPanel = null;
        try {
            postListPanel = new PostListPanel(displayPanel, contentPanel, undeletedPosts, user, comments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        displayPanel.add(postListPanel);
    }

    public void searchPostsUserNameKeyword(List<Post> undeletedPosts, String text) throws IOException {
        displayPanel.removeAll();

        List<Post> copy = new ArrayList<>();

        for (Post post : undeletedPosts) {
            if (post.status().equals(Post.CREATED) &&
                    post.userName().contains(text)) {
                copy.add(post);
            }
        }

        PostListPanel postListPanel = new PostListPanel(displayPanel, contentPanel, copy, user, comments);
        displayPanel.add(postListPanel);

        displayPanel.setVisible(false);
        displayPanel.setVisible(true);
    }

    public void searchPostsTitleKeyword(List<Post> undeletedPosts, String text) {
        displayPanel.removeAll();

        List<Post> copy = new ArrayList<>();

        for (Post post : undeletedPosts) {
            if (post.status().equals(Post.CREATED) &&
                    post.title().contains(text)) {
                copy.add(post);
            }
        }

        PostListPanel postListPanel = null;
        try {
            postListPanel = new PostListPanel(displayPanel, contentPanel, copy, user, comments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        displayPanel.add(postListPanel);

        displayPanel.setVisible(false);
        displayPanel.setVisible(true);
    }


    public void searchPostsTextKeyword(List<Post> undeletedPosts, String text) {
        displayPanel.removeAll();

        List<Post> copy = new ArrayList<>();

        for (Post post : undeletedPosts) {
            if (post.status().equals(Post.CREATED) &&
                    post.text().contains(text)) {
                copy.add(post);
            }
        }

        PostListPanel postListPanel = null;
        try {
            postListPanel = new PostListPanel(displayPanel, contentPanel, copy, user, comments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        displayPanel.add(postListPanel);

        displayPanel.setVisible(false);
        displayPanel.setVisible(true);
    }
}
