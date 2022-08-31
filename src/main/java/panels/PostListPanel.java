package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.Post;
import models.User;

public class PostListPanel extends JPanel {
    private final List<Post> timeArray;
    private JPanel contentPanel;
    private JPanel displayPanel;
    private List<Post> posts;
    private User user;
    private int startIndex = 0;
    private int endIndex = 10;
    private JPanel panel;


    public PostListPanel(JPanel displayPanel, JPanel contentPanel, List<Post> posts, User user) {
        this.displayPanel = displayPanel;
        this.posts = posts;
        this.contentPanel = contentPanel;
        this.user = user;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300, 500));
        this.setBackground(new Color(100, 0, 0, 100));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        timeArray = new ArrayList<>();
        for (Post post : posts) {
            timeArray.add(post);
        }

        Collections.reverse(timeArray);

        createPostList();

        initButtonPanel();
    }

    private void createPostList() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(timeArray.size(), 1));
        panel.setOpaque(false);

        this.add(panel, BorderLayout.PAGE_START);

        int index = 0;
        for (int i = startIndex; i < timeArray.size(); i += 1) {
            panel.add(labelPanel(timeArray.get(i)));
            index += 1;
            if (index == endIndex) {
                break;
            }
        }
    }

    private JPanel labelPanel(Post post) {
        JPanel labelPanel = new JPanel();
        labelPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
                , BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        labelPanel.setBackground(new Color(0, 100, 0, 100));
        labelPanel.setLayout(new BorderLayout());
        labelPanel.setPreferredSize(new Dimension(400, 40));

        JLabel postTitleLable = new JLabel(" 제목: " + post.title());
        postTitleLable.setForeground(Color.white);
        labelPanel.add(postTitleLable, BorderLayout.WEST);

        JLabel postUserNameLable = new JLabel("[글쓴이: " + post.userName() + "]   " + post.time());
        postUserNameLable.setForeground(Color.white);
        labelPanel.add(postUserNameLable, BorderLayout.EAST);

        labelPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPanel.removeAll();

                PostPanel postPanel = new PostPanel(posts, post, contentPanel, user);
                contentPanel.add(postPanel);

                contentPanel.setVisible(false);
                contentPanel.setVisible(true);
            }
        });
        return labelPanel;
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

            createPostList();

            this.setVisible(false);
            this.setVisible(true);
        });
        return button1;
    }

    public JPanel panel() {
        return panel;
    }
}
