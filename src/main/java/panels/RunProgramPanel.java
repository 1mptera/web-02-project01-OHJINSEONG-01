package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import models.DailyPlan;
import models.ExerciseCycle;
import models.Program;

public class RunProgramPanel extends JPanel {

    private final JPanel checkPanel;

    public RunProgramPanel(Program program) {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(100, 0, 0, 100));
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel, BorderLayout.PAGE_START);

        checkPanel = new JPanel();
        checkPanel.setPreferredSize(new Dimension(400, 500));
        checkPanel.setBackground(new Color(100, 0, 0, 100));
        this.add(checkPanel);

        for (DailyPlan dailyPlan : program.dailyPlans()) {
            if (!dailyPlan.day().equals("")) {
                JPanel panel = new JPanel();
                panel.setOpaque(false);
                panel.add(dayButton(dailyPlan));
                buttonPanel.add(panel);
            }
        }
    }

    private JButton dayButton(DailyPlan dailyPlan) {
        JButton button = new JButton(dailyPlan.day());
        button.addActionListener(e -> {
            checkPanel.removeAll();

            JPanel panel = new JPanel();
            panel.setOpaque(false);
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.setLayout(new GridLayout(dailyPlan.exerciseCycles().size(), 1));
            checkPanel.add(panel);

            int i = 1;

            for (ExerciseCycle exerciseCycle : dailyPlan.exerciseCycles()) {
                JPanel panel1 = new JPanel();
                panel1.setOpaque(false);
                panel1.setLayout(new GridLayout(3, 1));
                panel1.setPreferredSize(new Dimension(300, 100));
                panel1.add(exerciseNameLabel(i, exerciseCycle));
                panel1.add(repsAndSetLabel(exerciseCycle));
                panel1.add(new JTextField(10));
                i += 1;
                panel.add(panel1);
            }

            checkPanel.setVisible(false);
            checkPanel.setVisible(true);
        });
        return button;
    }

    private JLabel exerciseNameLabel(int i, ExerciseCycle exerciseCycle) {
        JLabel label = new JLabel(i + ". " + exerciseCycle.exercise().name());
        label.setForeground(Color.white);
        return label;
    }

    private JLabel repsAndSetLabel(ExerciseCycle exerciseCycle) {
        JLabel label = new JLabel(exerciseCycle.set() + " X " + exerciseCycle.reps());
        label.setForeground(Color.white);
        return label;
    }
}
