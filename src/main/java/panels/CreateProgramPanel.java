package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
import utils.DailyPlansLoader;
import utils.ProgramsLoader;

public class CreateProgramPanel extends JPanel {

    private JPanel confirmPanel;
    private JPanel listPanel;
    private List<DailyPlan> dailyPlans;
    private User user;
    private List<Program> programs;
    private Program program;
    private DailyPlan dailyPlan;
    private List<ExerciseCycle> exerciseCycles;
    private CreateDailyPlanPanel createDailyPlanPanel;
    private JTextField inputTitleTextField;
    private int createdDailyPlanId;
    private DailyPlan dailyPlan1;
    private DailyPlan dailyPlan11;

    public CreateProgramPanel(List<DailyPlan> dailyPlans, User user, List<Program> programs, Program program
            , List<ExerciseCycle> exerciseCycles) {
        this.dailyPlans = dailyPlans;
        this.user = user;
        this.programs = programs;
        this.program = program;
        this.dailyPlan = dailyPlan;
        this.exerciseCycles = exerciseCycles;

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
                    if (program.id() == dailyPlan.programId()) {
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

            int index = 0;

            for (DailyPlan dailyPlan1 : dailyPlans) {
                if (dailyPlan1.day().equals(day) && program.id() == dailyPlan1.programId()) {
                    createDailyPlanPanel = new CreateDailyPlanPanel(dailyPlans, day, user, programs, dailyPlan1, program, exerciseCycles);
                    index = 1;
                    break;
                }
            }

            if (index == 0) {
                createDailyPlanId();

                dailyPlan11 = new DailyPlan(new ArrayList<>(), day, "MAKING", program.id(), createdDailyPlanId);
                dailyPlans.add(dailyPlan11);

                saveDailyPlans();

                createDailyPlanPanel = new CreateDailyPlanPanel(dailyPlans, day, user, programs, dailyPlan11, program, exerciseCycles);
            }


            this.add(createDailyPlanPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private void createDailyPlanId() {
        DailyPlansLoader dailyPlansLoader = new DailyPlansLoader();
        try {
            createdDailyPlanId = dailyPlansLoader.createId();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void saveDailyPlans() {
        DailyPlansLoader dailyPlansLoader = new DailyPlansLoader();
        try {
            dailyPlansLoader.save(dailyPlans);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JLabel dailyPlanLabel(ExerciseCycle exerciseCycle) {
        JLabel label = new JLabel(exerciseCycle.exercise() + " 세트수 " + exerciseCycle.set() + " 수행횟수 " + exerciseCycle.reps());
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

            MyProgramPanel myProgramPanel = new MyProgramPanel(user, programs, dailyPlans, exerciseCycles);
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

            List<DailyPlan> copy = new ArrayList<>();

            program.updateTitle(inputTitleTextField.getText());

            for (DailyPlan dailyPlan1 : dailyPlans) {
                if (program.id() == dailyPlan1.programId()) {
                    copy.add(dailyPlan1);
                }
            }
            program.updateList(copy);

            program.updateStatus("CREATED");

            savePrograms();

            MyProgramPanel myProgramPanel = new MyProgramPanel(user, programs, dailyPlans, exerciseCycles);
            this.add(myProgramPanel);

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
}
