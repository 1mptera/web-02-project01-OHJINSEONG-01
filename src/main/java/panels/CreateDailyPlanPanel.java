package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import models.DailyPlan;
import models.Exercise;
import models.ExerciseCycle;
import models.Program;
import models.User;
import utils.DailyPlansLoader;
import utils.ExerciseCyclesLoader;

public class CreateDailyPlanPanel extends JPanel {

    private final String[] exerciseTypes;
    private final List<ExerciseCycle> exerciseCycles;
    private List<ExerciseCycle> copy;
    private JScrollPane scrollSingle;
    private JPanel jpList;
    private GridBagConstraints constraint;
    private GridBagLayout gbl;
    private List<Exercise> exercises;
    private List<DailyPlan> dailyPlans;
    private String day;
    private User user;
    private List<Program> programs;
    private Program program;
    private JTextField inputSetNumberTextField;
    private JTextField inputRepsTextField;
    private JPanel addExerciseCyclePanel;
    private JPanel addExerciseCycleListPanel;
    private JPanel addExerciseCycleButtonPanel;
    private ExerciseCycle exerciseCycle;
    private DailyPlan dailyPlan;
    private int createdDailyPlanId;

    public CreateDailyPlanPanel(List<DailyPlan> dailyPlans, String day, User user, List<Program> programs
            , DailyPlan dailyPlan, Program program, List<ExerciseCycle> exerciseCycles) {
        this.dailyPlans = dailyPlans;
        this.day = day;
        this.user = user;
        this.programs = programs;
        this.dailyPlan = dailyPlan;
        this.program = program;
        this.exerciseCycles = exerciseCycles;

        addexercises();

        exerciseTypes = new String[]{"가슴", "등", "어깨", "하체", "팔"};

        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        selectExerciseTypeButtons();
    }

    private void selectExerciseTypeButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(100, 0, 10, 50));
        this.add(panel, BorderLayout.PAGE_START);

        JPanel lablePanel = new JPanel();
        lablePanel.setOpaque(false);
        panel.add(lablePanel, BorderLayout.PAGE_START);
        lablePanel.add(titleLabel());

        JPanel buttonPanal = new JPanel();
        buttonPanal.setOpaque(false);
        panel.add(buttonPanal);

        for (String exerciseType : exerciseTypes) {
            buttonPanal.add(selectExerciseTypeButton(exerciseType));
        }
    }

    private JLabel titleLabel() {
        JLabel label = new JLabel("추가 하고 싶은 운동을 골라주세요!");
        label.setForeground(Color.WHITE);
        return label;
    }

    private JLabel exerciseCycleLabel(ExerciseCycle exerciseCycle) {
        JLabel label = new JLabel(exerciseCycle.exercise() + " 세트수 " + exerciseCycle.set() + " 수행횟수 " + exerciseCycle.reps());
        label.setForeground(Color.white);
        return label;
    }

    private void addExerciseCyclePanel() {
        addExerciseCyclePanel = new JPanel();
        addExerciseCyclePanel.setLayout(new BorderLayout());
        addExerciseCyclePanel.setOpaque(false);
        addExerciseCyclePanel.setPreferredSize(new Dimension(300, 270));
        this.add(addExerciseCyclePanel, BorderLayout.SOUTH);

        addExerciseCycleListPanel = new JPanel();

        addExerciseCycleListPanel.setBackground(new Color(100, 0, 0, 100));
        addExerciseCycleListPanel.setPreferredSize(new Dimension(300, 220));
        addExerciseCyclePanel.add(addExerciseCycleListPanel, BorderLayout.NORTH);

        for (ExerciseCycle exerciseCycle : exerciseCycles) {
            if (!exerciseCycle.status().equals("deleted")) {
                if (dailyPlan.id() == exerciseCycle.dailyPlanId()) {
                    JPanel panel = new JPanel();
                    panel.setBackground(new Color(0, 100, 0, 100));
                    panel.setPreferredSize(new Dimension(350, 30));
                    panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
                            , BorderFactory.createEmptyBorder(2, 5, 5, 5)));
                    panel.add(exerciseCycleLabel(exerciseCycle));
                    addExerciseCycleListPanel.add(panel);
                }
            }
        }

        addExerciseCycleButtonPanel = new JPanel();
        addExerciseCycleButtonPanel.setOpaque(false);
        addExerciseCycleButtonPanel.setPreferredSize(new Dimension(300, 50));
        addExerciseCycleButtonPanel.add(createDailyPlanButton());
        addExerciseCyclePanel.add(addExerciseCycleButtonPanel, BorderLayout.SOUTH);
    }

    private JButton selectExerciseTypeButton(String exerciseType) {
        JButton button = new JButton(exerciseType);
        button.addActionListener(e -> {
            this.removeAll();

            selectExerciseTypeButtons();

            int i = 0;

            constraint = new GridBagConstraints();
            gbl = new GridBagLayout();

            jpList = new JPanel(gbl);

            scrollSingle = new JScrollPane(jpList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollSingle.setPreferredSize(new Dimension(400, 300));

            for (Exercise exercise : exercises) {
                if (exerciseType.equals(exercise.type())) {
                    JPanel addExerciseCyclePanel = new JPanel();

                    addExerciseCyclePanel.setPreferredSize(new Dimension(380, 100));
                    addExerciseCyclePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)
                            , BorderFactory.createLineBorder(Color.BLACK)));

                    addExerciseCyclePanel.add(new JLabel(exercise.name()));
                    addExerciseCyclePanel.add(addExerciseButton(exercise));
                    addExerciseCyclePanel.add(deleteExerciseButton(exercise));
                    gbAdd(addExerciseCyclePanel, 0, i, 1, 1, 1, 1, jpList);
                    i += 1;
                }
            }
            this.add(scrollSingle);

            addExerciseCyclePanel();

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton addExerciseButton(Exercise exercise) {
        JButton button = new JButton("추가");
        button.addActionListener(e -> {
            int index = 0;

            copy = new ArrayList<>();

            for (ExerciseCycle exerciseCycle1 : exerciseCycles) {
                if (dailyPlan.id() == exerciseCycle1.dailyPlanId()) {
                    copy.add(exerciseCycle1);
                }
            }

            for (ExerciseCycle exerciseCycle1 : copy) {
                if (exerciseCycle1.exercise().equals(exercise)) {
                    index += 1;
                    break;
                }
            }
            if (index == 0) {
                JFrame frame = new JFrame();
                frame.setSize(400, 200);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                frame.add(panel, BorderLayout.PAGE_START);

                panel.add(exerciseNameLabel(exercise));

                JPanel panel2 = new JPanel();
                frame.add(panel2);

                JPanel inputPanel = new JPanel();
                panel2.add(inputPanel);
                panel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                inputPanel.setPreferredSize(new Dimension(350, 75));
                inputPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
                        , BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                inputPanel.setLayout(new GridLayout(2, 3));
                inputPanel.add(new JLabel("       세트 수"));
                inputPanel.add(new JLabel(""));
                inputPanel.add(new JLabel("     수행 횟수"));

                inputPanel.add(inputSetNumberTextField());
                inputPanel.add(new JLabel("            X"));
                inputPanel.add(inputRepsTextField());

                JPanel panel3 = new JPanel();
                frame.add(panel3, BorderLayout.SOUTH);
                panel3.add(addExerciseCycleButton(frame, exercise));

                frame.setVisible(true);
            }
        });
        return button;
    }

    private JButton createDailyPlanButton() {
        JButton button = new JButton("계획 하기");
        button.addActionListener(e -> {
            this.removeAll();
            List<ExerciseCycle> copy = new ArrayList<>();

            for (ExerciseCycle exerciseCycle1 : exerciseCycles) {
                if (dailyPlan.id() == exerciseCycle1.dailyPlanId()) {
                    copy.add(exerciseCycle1);
                }
            }
            dailyPlan.updateDay(day);

            dailyPlan.updateList(copy);

            dailyPlan.updateStatus("CREATED");

            saveDailyPlans();

            CreateProgramPanel createProgramPanel = new CreateProgramPanel(dailyPlans, user, programs, program, exerciseCycles);
            this.add(createProgramPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private void saveDailyPlans() {
        DailyPlansLoader dailyPlansLoader = new DailyPlansLoader();
        try {
            dailyPlansLoader.save(dailyPlans);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JTextField inputSetNumberTextField() {
        inputSetNumberTextField = new JTextField(10);
        return inputSetNumberTextField;
    }

    private JTextField inputRepsTextField() {
        inputRepsTextField = new JTextField(10);
        return inputRepsTextField;
    }

    private JButton deleteExerciseButton(Exercise exercise) {
        JButton button = new JButton("삭제");
        button.addActionListener(e -> {
            addExerciseCyclePanel.removeAll();
            for (ExerciseCycle exerciseCycle1 : exerciseCycles) {
                if (exerciseCycle1.exercise().equals(exercise)) {
                    exerciseCycle1.deleted();
                }
            }

            saveExerciseCycles();

            addExerciseCyclePanel();

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton addExerciseCycleButton(JFrame frame, Exercise exercise) {
        JButton button = new JButton("추가");
        button.addActionListener(e -> {
            addExerciseCyclePanel.removeAll();
            addExerciseCycleListPanel.setBackground(new Color(0, 0, 0, 0));

            int sets = Integer.parseInt(inputSetNumberTextField.getText());
            int reps = Integer.parseInt(inputRepsTextField.getText());

            exerciseCycle = new ExerciseCycle(exercise.name(), sets, reps, "added", dailyPlan.id());
            exerciseCycles.add(exerciseCycle);

            copy = new ArrayList<>();

            for (ExerciseCycle exerciseCycle1 : exerciseCycles) {
                if (dailyPlan.id() == exerciseCycle1.dailyPlanId()) {
                    copy.add(exerciseCycle1);
                }
            }

            saveExerciseCycles();

            addExerciseCyclePanel();

            this.setVisible(false);
            this.setVisible(true);

            frame.dispose();
        });
        return button;
    }

    private void saveExerciseCycles() {
        ExerciseCyclesLoader exerciseCyclesLoader = new ExerciseCyclesLoader();
        try {
            exerciseCyclesLoader.save(exerciseCycles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JLabel exerciseNameLabel(Exercise exercise) {
        JLabel label = new JLabel(exercise.name());
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        return label;
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

    private void addexercises() {
        exercises = new ArrayList<>();
        exercises.add(new Exercise("벤치프레스", "가슴"));
        exercises.add(new Exercise("인클라인 벤치프레스", "가슴"));
        exercises.add(new Exercise("덤벨 벤치프레스", "가슴"));
        exercises.add(new Exercise("인클라인 덤벨 벤치프레스", "가슴"));
        exercises.add(new Exercise("딥스", "가슴"));
        exercises.add(new Exercise("덤벨 플라이", "가슴"));
        exercises.add(new Exercise("케이블 플라이", "가슴"));
        exercises.add(new Exercise("케이블 크로스오버", "가슴"));
        exercises.add(new Exercise("체스트 프레스 머신", "가슴"));
        exercises.add(new Exercise("펙덱 플라이 머신", "가슴"));
        exercises.add(new Exercise("푸시업", "가슴"));
        exercises.add(new Exercise("인클라인 덤벨 플라이", "가슴"));

        exercises.add(new Exercise("풀업", "등"));
        exercises.add(new Exercise("바벨 로우", "등"));
        exercises.add(new Exercise("덤벨 로우", "등"));
        exercises.add(new Exercise("펜들레이 로우", "등"));
        exercises.add(new Exercise("시티드 로우 머신", "등"));
        exercises.add(new Exercise("랫풀 다운", "등"));
        exercises.add(new Exercise("친업", "등"));
        exercises.add(new Exercise("시티드 케이블 로우", "등"));
        exercises.add(new Exercise("루마니안 데드리프트", "등"));
        exercises.add(new Exercise("원암 덤벨 로우", "등"));
        exercises.add(new Exercise("바벨 풀오버", "등"));
        exercises.add(new Exercise("티바 로우", "등"));

        exercises.add(new Exercise("스미스머신 오버헤드 프레스", "어깨"));
        exercises.add(new Exercise("덤벨 레터널 레이즈", "어깨"));
        exercises.add(new Exercise("오버헤드 프레스", "어깨"));
        exercises.add(new Exercise("덤벨 숄더 프레스", "어깨"));
        exercises.add(new Exercise("덤벨 프론트 레이즈", "어깨"));
        exercises.add(new Exercise("비하인드 넥 프레스", "어깨"));
        exercises.add(new Exercise("페이스 풀", "어깨"));
        exercises.add(new Exercise("바벨 업라이트 로우", "어깨"));

        exercises.add(new Exercise("바벨 백스쿼트", "하체"));
        exercises.add(new Exercise("컨벤셔널 데드리프트", "하체"));
        exercises.add(new Exercise("프론트 스쿼트", "하체"));
        exercises.add(new Exercise("레그 프레스", "하체"));
        exercises.add(new Exercise("레그 컬", "하체"));
        exercises.add(new Exercise("레그 익스텐션", "하체"));
        exercises.add(new Exercise("스모 데드리프트", "하체"));
        exercises.add(new Exercise("점프 스쿼트", "하체"));
        exercises.add(new Exercise("브이 스쿼트", "하체"));
        exercises.add(new Exercise("힙 어브덕션 머신", "하체"));

        exercises.add(new Exercise("바벨 컬", "팔"));
        exercises.add(new Exercise("덤벨 컬", "팔"));
        exercises.add(new Exercise("덤벨 해머 컬", "팔"));
        exercises.add(new Exercise("케이블 푸쉬 다운", "팔"));
        exercises.add(new Exercise("클로즈 그립 벤치프레스", "팔"));
        exercises.add(new Exercise("덤벨 프리쳐 컬", "팔"));
        exercises.add(new Exercise("바벨 프리쳐 컬", "팔"));
        exercises.add(new Exercise("암 컬 머신", "팔"));
        exercises.add(new Exercise("바벨 라잉 트라이셉 익스텐션", "팔"));
    }
}
