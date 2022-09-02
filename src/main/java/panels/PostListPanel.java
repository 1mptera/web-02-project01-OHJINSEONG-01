package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.Comment;
import models.Post;
import models.User;
import utils.PostsLoader;

public class PostListPanel extends JPanel {
    private final List<Post> timeArray;
    private JPanel contentPanel;
    private JPanel displayPanel;
    private List<Post> posts;
    private User user;
    private List<Comment> comments;
    private int startIndex = 0;
    private int endIndex = 10;
    private JPanel panel;

    public PostListPanel(JPanel displayPanel, JPanel contentPanel, List<Post> posts, User user
            , List<Comment> comments) throws IOException {
        this.displayPanel = displayPanel;
        this.posts = posts;
        this.contentPanel = contentPanel;
        this.user = user;
        this.comments = comments;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800, 580));
        this.setBackground(new Color(100, 0, 0, 100));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        timeArray = new ArrayList<>();

        for (Post post : posts) {
            timeArray.add(post);
        }

        Collections.reverse(timeArray);

        createPostList();

        initButtonPanel();
    }

    private void createPostList() throws IOException {
        panel = new JPanel();
        panel.setLayout(new GridLayout(timeArray.size() + 1, 1));
        panel.setOpaque(false);

        this.add(panel, BorderLayout.PAGE_START);

        JPanel classifyPanel = new JPanel();
        panel.add(classifyPanel);
        classifyPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 28));
        classifyPanel.setBackground(new Color(0, 100, 0, 100));
        classifyPanel.setLayout(new BorderLayout());

        JLabel classifyLabel1 = new JLabel("제목");
        classifyLabel1.setForeground(Color.white);
        classifyPanel.add(classifyLabel1, BorderLayout.WEST);

        JLabel classifyLabel2 = new JLabel("작성자                조회수  추천   댓글수       시간");
        classifyLabel2.setForeground(Color.white);
        classifyPanel.add(classifyLabel2, BorderLayout.EAST);

        if (timeArray.size() == 0) {
            JLabel alertLabel = new JLabel("게시물이 없습니다!");
            alertLabel.setHorizontalAlignment(JLabel.CENTER);
            alertLabel.setForeground(Color.white);
            panel.add(alertLabel);
        }

        if (timeArray.size() != 0) {
            int index = 0;
            for (int i = startIndex; i < timeArray.size(); i += 1) {
                panel.add(labelPanel(timeArray.get(i)));
                index += 1;
                if (index == endIndex) {
                    break;
                }
            }
        }
    }

    private JPanel labelPanel(Post post) throws IOException {
        JPanel labelPanel = new JPanel();
        labelPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
                , BorderFactory.createEmptyBorder(0, 3, 3, 3)));
        labelPanel.setBackground(new Color(0, 100, 0, 100));
        labelPanel.setLayout(new BorderLayout());
        labelPanel.setPreferredSize(new Dimension(750, 45));

        BufferedImage questionImage = ImageIO.read(new File("./src/main/resources/questionImage.png"));

        JPanel imageAndTitlePanel = new JPanel();
        imageAndTitlePanel.setLayout(new BorderLayout());
        imageAndTitlePanel.setPreferredSize(new Dimension(430, 40));
        imageAndTitlePanel.setOpaque(false);
        labelPanel.add(imageAndTitlePanel, BorderLayout.WEST);

        JLabel imageLabel = new JLabel(new ImageIcon(questionImage));
        imageAndTitlePanel.add(imageLabel, BorderLayout.WEST);

        JLabel postTitleLable = new JLabel("  " + post.title());
        postTitleLable.setForeground(Color.white);
        imageAndTitlePanel.add(postTitleLable);

        JPanel userNamePanel = new JPanel();
        userNamePanel.setOpaque(false);

        JLabel userNameLabel = new JLabel("[" + post.userName() + "]");
        userNameLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        userNameLabel.setForeground(Color.white);
        userNamePanel.add(userNameLabel);
        labelPanel.add(userNamePanel);

        JLabel postCountsLabel = new JLabel("       " + post.viewCount() + "       "
                + post.likeCount() + "      " + commentsCount(post) + "     " + post.classifyTime());
        postCountsLabel.setForeground(Color.white);

        JPanel postCountsPanel = new JPanel();
        postCountsPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        postCountsPanel.setPreferredSize(new Dimension(200, 40));
        postCountsPanel.setOpaque(false);
        postCountsPanel.add(postCountsLabel);

        labelPanel.add(postCountsPanel, BorderLayout.EAST);

        labelPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPanel.removeAll();

                post.addViewCount();

                savePosts();

                PostPanel postPanel = new PostPanel(posts, post, contentPanel, user, comments);
                contentPanel.add(postPanel);

                contentPanel.setVisible(false);
                contentPanel.setVisible(true);
            }
        });
        return labelPanel;
    }

    private int commentsCount(Post post) {
        int commentsCount = 0;
        for (Comment comment : comments) {
            if (comment.status().equals("DELETED")) {
                if (post.id() == comment.postId()) {
                    commentsCount += 1;
                }
            }
        }
        return commentsCount;
    }

    private void savePosts() {
        PostsLoader postsLoader = new PostsLoader();
        try {
            postsLoader.save(posts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(100, 50));
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        double undeletedPostsSize = posts.size();
        double index = Math.floor((undeletedPostsSize - 1) / 10) + 1;

        List<String> numbers = new ArrayList<>();

        for (int i = 1; i <= index; i += 1) {
            numbers.add(String.valueOf(i));
        }

        for (String number : numbers) {
            JButton numberButton = numberButtons(number);
            buttonPanel.add(numberButton);
        }
    }

    private JButton numberButtons(String number) {
        JButton button1 = new JButton(number);
        button1.addActionListener(e -> {
            panel.removeAll();

            startIndex = Integer.parseInt(number) * 10 - 10;
            endIndex = Integer.parseInt(number) * 10;

            try {
                createPostList();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            this.setVisible(false);
            this.setVisible(true);
        });
        return button1;
    }

    public JPanel panel() {
        return panel;
    }
}
