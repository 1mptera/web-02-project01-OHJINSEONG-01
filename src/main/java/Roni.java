import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import models.Comment;
import models.DailyPlan;
import models.ExerciseCycle;
import models.Post;
import models.Program;
import models.User;
import panels.ImagePanel;
import panels.LogInPanel;
import panels.MyProgramPanel;
import panels.SharePostPanel;
import panels.UserAccountPanel;
import utils.CommentsLoader;
import utils.DailyPlansLoader;
import utils.ExerciseCyclesLoader;
import utils.PostsLoader;
import utils.ProgramsLoader;
import utils.UsersLoader;

public class Roni {
    private JFrame frame;
    private JPanel contentPanel;
    private ImagePanel imagePanel;
    private User user;
    private List<User> users;
    private JPanel menuPanel;
    private JPanel logInButtonPanel;
    private List<Program> programs;
    private List<Post> posts;
    private List<Comment> comments;
    private List<ExerciseCycle> exerciseCycles;
    private List<DailyPlan> dailyPlans;

    public static void main(String[] args) throws FileNotFoundException {
        Roni roni = new Roni();

        roni.run();
    }

    public Roni() throws FileNotFoundException {
        ExerciseCyclesLoader exerciseCyclesLoader = new ExerciseCyclesLoader();
        DailyPlansLoader dailyPlansLoader = new DailyPlansLoader();
        ProgramsLoader programsLoader = new ProgramsLoader();
        UsersLoader usersLoader = new UsersLoader();
        PostsLoader postsLoader = new PostsLoader();
        CommentsLoader commentsLoader = new CommentsLoader();

        this.exerciseCycles = exerciseCyclesLoader.load();
        this.dailyPlans = dailyPlansLoader.load(exerciseCycles);
        this.programs = programsLoader.load(dailyPlans);
        this.users = usersLoader.load();
        this.posts = postsLoader.load();
        this.comments = commentsLoader.load();
    }

    private void run() {
        frame = new JFrame("Roni");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initImagePanel();

        initMenuPanel();

        initContentPanel();

        frame.setVisible(true);
    }

    private void initImagePanel() {
        imagePanel = new ImagePanel("./src/main/resources/img.png");
        imagePanel.setLayout(new BorderLayout());
        frame.add(imagePanel);
    }

    private void initMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        imagePanel.add(menuPanel, BorderLayout.PAGE_START);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        menuPanel.add(panel);
        panel.add(searchProgramButton());
        panel.add(createProgramButton());
        panel.add(shareExerciseBorad());

        logInButtonPanel = new JPanel();
        logInButtonPanel.setOpaque(false);
        menuPanel.add(logInButtonPanel);

        int index = 0;

        for (User user : users) {
            if (user.status().equals(User.LOGIN)) {
                logInButtonPanel.add(getUserAccountButton(user));
                index = 1;
                break;
            }
        }

        if (index == 0) {
            logInButtonPanel.add(logInButton());
        }
    }

    private JButton searchProgramButton() {
        JButton button = new JButton("프로그램 검색");
        button.addActionListener(e -> {
            for (User user : users) {
                if (user.status().equals(User.LOGIN)) {

                }
            }
        });
        return button;
    }

    private JButton createProgramButton() {
        JButton button = new JButton("나의 프로그램");
        button.addActionListener(e -> {
            for (User user : users) {
                if (user.status().equals(User.LOGIN)) {
                    contentPanel.removeAll();

                    JPanel myProgramPanel = new MyProgramPanel(user, programs, dailyPlans,exerciseCycles);
                    contentPanel.add(myProgramPanel);

                    contentPanel.setVisible(false);
                    contentPanel.setVisible(true);
                }
            }
        });
        return button;
    }

    private JButton shareExerciseBorad() {
        JButton button = new JButton("운동 게시판");
        button.addActionListener(e -> {
            for (User user : users) {
                if (user.status().equals(User.LOGIN)) {
                    contentPanel.removeAll();

                    SharePostPanel sharePostPanel = new SharePostPanel(posts, contentPanel, user, comments);

                    contentPanel.add(sharePostPanel);

                    contentPanel.setVisible(false);
                    contentPanel.setVisible(true);
                    frame.setVisible(true);
                }
            }
        });
        return button;
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

    private JButton getUserAccountButton(User user) {
        JButton button = new JButton(user.name());
        button.addActionListener(e -> {
            contentPanel.removeAll();

            JPanel userAccountPanel = new UserAccountPanel(user, users, contentPanel, logInButtonPanel, comments, posts);
            contentPanel.add(userAccountPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
        });
        return button;
    }

    private void initContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        imagePanel.add(contentPanel);
    }
}
