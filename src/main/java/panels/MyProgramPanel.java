package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.DailyPlan;
import models.ExerciseCycle;
import models.Program;
import models.User;
import utils.ProgramsLoader;

public class MyProgramPanel extends JPanel {
    private User user;
    private List<Program> programs;
    private List<ExerciseCycle> exerciseCycles;
    private List<DailyPlan> dailyPlans;
    private JPanel programListPanel;
    private int createdProgramId;

    public MyProgramPanel(User user, List<Program> programs, List<DailyPlan> dailyPlans, List<ExerciseCycle> exerciseCycles) {
        this.user = user;
        this.programs = programs;
        this.exerciseCycles = exerciseCycles;
        this.dailyPlans = dailyPlans;

        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        this.setOpaque(false);
        this.add(panel, BorderLayout.PAGE_START);

        initProgramListPanel();

        showProgramPanel();

        panel.add(selectProgramButton());
        panel.add(createProgramButton());
    }

    private void initProgramListPanel() {
        programListPanel = new JPanel();
        programListPanel.setOpaque(false);
        this.add(programListPanel);
    }

    private JButton selectProgramButton() {
        JButton button = new JButton("프로그램 선택");
        button.addActionListener(e -> {
            showProgramPanel();
        });
        return button;
    }

    private void showProgramPanel() {
        programListPanel.removeAll();
        programListPanel.setLayout(new GridLayout(programs.size(), 1));

        for (Program program : programs) {
            if (program.status().equals(Program.CREATED) || program.status().equals(Program.SHARED)) {
                if (user.id() == program.userId()) {
                    JPanel panel = new JPanel();
                    panel.setBackground(new Color(100, 0, 0, 150));
                    panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    JPanel panel1 = new JPanel();
                    panel1.setOpaque(false);
                    panel.add(panel1);
                    panel1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
                            , BorderFactory.createEmptyBorder(5, 10, 5, 10)));
                    panel1.setPreferredSize(new Dimension(300, 130));
                    panel1.setLayout(new BorderLayout());

                    JPanel titlePanel = new JPanel();
                    titlePanel.setOpaque(false);
                    panel1.add(titlePanel, BorderLayout.PAGE_START);

                    JPanel userNamePanel = new JPanel();
                    userNamePanel.setBorder(BorderFactory.createEmptyBorder(20, 90, 0, 0));
                    userNamePanel.setOpaque(false);
                    panel1.add(userNamePanel);

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setOpaque(false);
                    buttonPanel.setLayout(new FlowLayout());
                    panel1.add(buttonPanel, BorderLayout.SOUTH);

                    titlePanel.add(titleLabel(program));
                    userNamePanel.add(userNameLabel(program));
                    buttonPanel.add(runProgramButton(program));
                    buttonPanel.add(shareProgramButton(program));
                    buttonPanel.add(deleteProgramButton(program));
                    programListPanel.add(panel);
                }
                this.setVisible(false);
                this.setVisible(true);
            }
        }
    }

    private JButton shareProgramButton(Program program) {
        JButton button = new JButton("공유하기");
        button.addActionListener(e -> {
            this.removeAll();

            program.updateStatus("SHARED");

            savePrograms();

            MyProgramPanel myProgramPanel = new MyProgramPanel(user, programs, dailyPlans, exerciseCycles);
            this.add(myProgramPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton deleteProgramButton(Program program) {
        JButton button = new JButton("삭제");
        button.addActionListener(e -> {
            this.removeAll();

            program.delete();
            savePrograms();

            MyProgramPanel myProgramPanel = new MyProgramPanel(user, programs, dailyPlans, exerciseCycles);
            this.add(myProgramPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton runProgramButton(Program program) {
        JButton button = new JButton("실행");
        button.addActionListener(e -> {
            programListPanel.removeAll();

            RunProgramPanel runProgramPanel = new RunProgramPanel(program);
            programListPanel.add(runProgramPanel);

            runProgramPanel.completeButtonPanel().add(compeleteButton(program));

            programListPanel.setVisible(false);
            programListPanel.setVisible(true);
        });
        return button;
    }

    private JButton compeleteButton(Program program) {
        JButton button = new JButton("완료");
        button.addActionListener(e -> {
            programListPanel.removeAll();

            RunProgramPanel runProgramPanel = new RunProgramPanel(program);
            programListPanel.add(runProgramPanel);

            runProgramPanel.completeButtonPanel().add(compeleteButton(program));

            programListPanel.setVisible(false);
            programListPanel.setVisible(true);
        });
        return button;
    }

    private JLabel titleLabel(Program program) {
        JLabel label = new JLabel(program.title());
        label.setFont(new Font("Serif", Font.BOLD, 18));
        label.setForeground(Color.white);
        return label;
    }

    private JLabel userNameLabel(Program program) {
        JLabel label = new JLabel("아이디: " + program.userName());
        label.setForeground(Color.white);
        return label;
    }

    private JButton createProgramButton() {
        JButton button = new JButton("프로그램 만들기");
        button.addActionListener(e -> {
            this.removeAll();

            createProgramId();
            Program program = new Program("", new ArrayList<>(), 0, user.userName(), user.id(), createdProgramId, "MAKING");
            programs.add(program);

            savePrograms();

            JPanel createProgramPanel = new CreateProgramPanel(dailyPlans, user, programs, program, exerciseCycles);
            this.add(createProgramPanel);

            this.setVisible(false);
            this.setVisible(true);

        });
        return button;
    }

    private void savePrograms() {
        ProgramsLoader programsLoader = new ProgramsLoader();
        try {
            programsLoader.save(programs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createProgramId() {
        ProgramsLoader programsLoader = new ProgramsLoader();
        try {
            createdProgramId = programsLoader.createId();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
