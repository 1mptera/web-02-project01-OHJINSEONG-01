import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import models.Program;
import models.User;
import panels.ImagePanel;
import panels.LogInPanel;
import panels.MyProgramPanel;
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

    public static void main(String[] args) throws FileNotFoundException {
        Roni roni = new Roni();

        roni.run();
    }

    public Roni() throws FileNotFoundException {
        UsersLoader usersLoader = new UsersLoader();

        users = new ArrayList<>();
        this.users = usersLoader.load();
    }

    private void run() {
        user = new User("", "", "", "", "", "");

        programs = new ArrayList<>();


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

        if (!user.status().equals("logIn")) {
            logInButtonPanel.add(logInButton());
        }
    }

    private JButton searchProgramButton() {
        JButton button = new JButton("프로그램 검색");
        return button;
    }

    private JButton createProgramButton() {
        JButton button = new JButton("나의 프로그램");
        button.addActionListener(e -> {
            for (User user1 : users) {
                if (user1.status().equals("logIn")) {
                    contentPanel.removeAll();

                    JPanel myProgramPanel = new MyProgramPanel(user1,programs);
                    contentPanel.add(myProgramPanel);

                    contentPanel.setVisible(false);
                    contentPanel.setVisible(true);
                }
            }
        });
        return button;
    }

    private JButton shareExerciseBorad() {
        JButton button = new JButton("운동 공유 게시판");
        return button;
    }

    private JButton logInButton() {
        JButton button = new JButton("로그인");
        button.addActionListener(e -> {
            contentPanel.removeAll();

            JPanel logInPanel = new LogInPanel(users, user, contentPanel, logInButtonPanel);
            logInButton((LogInPanel) logInPanel);
            contentPanel.add(logInPanel);

            contentPanel.setVisible(false);
            contentPanel.setVisible(true);
            frame.setVisible(true);
        });
        return button;
    }

    private JButton logInButton(LogInPanel logInPanel) {
        JButton button = logInPanel.logInButton();
        button.addActionListener(e -> {
            menuPanel.removeAll();

            initMenuPanel();

            menuPanel.setVisible(false);
            menuPanel.setVisible(true);
        });
        return button;
    }

    private void initContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        imagePanel.add(contentPanel);
    }
}
