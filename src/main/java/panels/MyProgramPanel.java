package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.DailyPlan;
import models.Program;
import models.User;

public class MyProgramPanel extends JPanel {
    private User user;
    private List<Program> programs;
    private JPanel programListPanel;

    public MyProgramPanel(User user, List<Program> programs) {
        this.user = user;
        this.programs = programs;

        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        this.setOpaque(false);
        this.add(panel, BorderLayout.PAGE_START);

        panel.add(selectProgramButton());
        panel.add(createProgramButton());

        initProgramListPanel();
    }

    private void initProgramListPanel() {
        programListPanel = new JPanel();
        programListPanel.setOpaque(false);
        this.add(programListPanel);
    }

    private JButton selectProgramButton() {
        JButton button = new JButton("프로그램 선택");
        button.addActionListener(e -> {
            programListPanel.removeAll();
            programListPanel.setLayout(new GridLayout(programs.size(), 1));

            for (Program program : programs) {
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
                userNamePanel.setBorder(BorderFactory.createEmptyBorder(20,90,0,0));
                userNamePanel.setOpaque(false);
                panel1.add(userNamePanel);

                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new FlowLayout());
                panel1.add(buttonPanel, BorderLayout.SOUTH);

                titlePanel.add(titleLabel(program));
                userNamePanel.add(userNameLabel(program));
                buttonPanel.add(runProgramButton(program));
                buttonPanel.add(new JButton("공유하기"));
                buttonPanel.add(new JButton("삭제"));
                programListPanel.add(panel);
            }

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

            programListPanel.setVisible(false);
            programListPanel.setVisible(true);
        });
        return button;
    }

    private JLabel titleLabel(Program program) {
        JLabel label = new JLabel(program.title());
        label.setFont(new Font("Serif",Font.BOLD,18));
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

            List<DailyPlan> dailyPlans = new ArrayList<>();
            dailyPlans.add(new DailyPlan(new ArrayList<>(), "", "created"));

            JPanel createProgramPanel = new CreateProgramPanel(dailyPlans, user, programs);
            this.add(createProgramPanel);

            this.setVisible(false);
            this.setVisible(true);

        });
        return button;
    }
}
