package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import models.DailyPlan;
import models.ExerciseCycle;
import models.Program;
import models.User;

public class CreateProgramPanel extends JPanel {

    private JPanel confirmPanel;
    private JPanel listPanel;
    private List<DailyPlan> dailyPlans;
    private User user;
    private List<Program> programs;
    private CreateDailyPlanPanel createDailyPlanPanel;
    private JTextField inputTitleTextField;

    public CreateProgramPanel(List<DailyPlan> dailyPlans, User user, List<Program> programs) {
        this.dailyPlans = dailyPlans;
        this.user = user;
        this.programs = programs;
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        initInputPanel();

        initShowDailyPlanPanel();

        initCreateProGramButton();
    }

    private void initInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        this.add(panel, BorderLayout.PAGE_START);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new GridLayout(4, 1));
        titlePanel.add(titleLabel());
        titlePanel.add(inputTitleTextField());
        titlePanel.add(new JLabel(""));
        titlePanel.add(dayLabel());
        panel.add(titlePanel, BorderLayout.PAGE_START);

        JPanel weekPanel = new JPanel();
        weekPanel.setOpaque(false);
        panel.add(weekPanel);

        String[] week = new String[]{"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};
        for (String day : week) {
            weekPanel.add(createDailyPlanPanel(day));
        }
    }

    private JTextField inputTitleTextField() {
        inputTitleTextField = new JTextField(10);
        return inputTitleTextField;
    }

    private void initShowDailyPlanPanel() {
        confirmPanel = new JPanel();
        this.add(confirmPanel);
        confirmPanel.setLayout(new BorderLayout());
        confirmPanel.setOpaque(false);
        confirmPanel.setPreferredSize(new Dimension(300, 400));

        listPanel = new JPanel();
        listPanel.setBackground(new Color(100, 0, 0, 100));
        listPanel.setPreferredSize(new Dimension(300, 300));
        confirmPanel.add(listPanel, BorderLayout.PAGE_START);
    }

    private JLabel titleLabel() {
        JLabel label = new JLabel("제목을 입력해주세요!");
        label.setForeground(Color.white);
        return label;
    }

    private JLabel dayLabel() {
        JLabel label = new JLabel("날짜를 선택해주세요!");
        label.setForeground(Color.white);
        return label;
    }

    private JButton createDailyPlanPanel(String day) {
        JButton button = new JButton(day);
        button.addActionListener(e -> {
            confirmPanel.removeAll();

            initShowDailyPlanPanel();
            for (DailyPlan dailyPlan : dailyPlans) {
                if (dailyPlan.day().equals(day)) {
                    for (ExerciseCycle exerciseCycle : dailyPlan.exerciseCycles()) {
                        if (!exerciseCycle.status().equals("deleted")) {
                            JPanel panel = new JPanel();
                            panel.setLayout(new FlowLayout());
                            panel.setBackground(new Color(0, 100, 0, 100));
                            panel.setPreferredSize(new Dimension(400, 50));
                            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
                                    , BorderFactory.createEmptyBorder(2, 5, 5, 5)));
                            panel.add(dailyPlanLabel(exerciseCycle));
                            panel.add(deleteExerciseCycleButton(exerciseCycle, panel));
                            listPanel.add(panel);

                        }
                    }
                }
            }

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(50, 50, 50, 100));
            buttonPanel.setOpaque(false);
            confirmPanel.add(buttonPanel);
            buttonPanel.add(modifyButton(day, dailyPlans));

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton deleteExerciseCycleButton(ExerciseCycle exerciseCycle, JPanel panel) {
        JButton button = new JButton("삭제");
        button.addActionListener(e -> {
            listPanel.remove(panel);
            exerciseCycle.deleted();

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton modifyButton(String day, List<DailyPlan> dailyPlans) {
        JButton button = new JButton("추가하기");
        button.addActionListener(e -> {
            this.removeAll();

            createDailyPlanPanel = new CreateDailyPlanPanel(dailyPlans, day, user, programs);

            this.add(createDailyPlanPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JLabel dailyPlanLabel(ExerciseCycle exerciseCycle) {
        JLabel label = new JLabel(exerciseCycle.exercise().name() + " 세트수 " + exerciseCycle.set() + " 수행횟수 " + exerciseCycle.reps());
        label.setForeground(Color.white);
        return label;
    }

    private void initCreateProGramButton() {
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.SOUTH);
        panel.setBackground(new Color(100, 100, 100, 100));

        panel.add(goFirstPageButton());
        panel.add(createProgramButton());
    }

    private JButton goFirstPageButton() {
        JButton button = new JButton("뒤로 가기");
        button.addActionListener(e -> {
            this.removeAll();

            MyProgramPanel myProgramPanel = new MyProgramPanel(user, programs);
            this.add(myProgramPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton createProgramButton() {
        JButton button = new JButton("프로그램 만들기");
        button.addActionListener(e -> {
            this.removeAll();

            Program program = new Program(inputTitleTextField.getText(), dailyPlans, 0, user.userName(), user.id());
            programs.add(program);

            MyProgramPanel myProgramPanel = new MyProgramPanel(user, programs);
            this.add(myProgramPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }
}
