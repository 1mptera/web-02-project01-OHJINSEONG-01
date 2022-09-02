package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import models.Program;
import models.User;
import utils.ProgramsLoader;

public class SearchProgramPanel extends JPanel {

    private final GridBagConstraints constraint;
    private final GridBagLayout gbl;
    private List<Program> programs;
    private User user;

    public SearchProgramPanel(List<Program> programs, User user) {
        this.programs = programs;
        this.user = user;
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        int i = 0;

        constraint = new GridBagConstraints();
        gbl = new GridBagLayout();

        JPanel jpList = new JPanel(gbl);

        JScrollPane scrollSingle = new JScrollPane(jpList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollSingle.setPreferredSize(new Dimension(510, 400));

        for (Program program : programs) {
            if (program.status().equals(Program.SHARED)) {
                JPanel addExerciseCyclePanel = new JPanel();

                addExerciseCyclePanel.setPreferredSize(new Dimension(500, 100));
                addExerciseCyclePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
                        , BorderFactory.createEmptyBorder(25, 10, 10, 10)));

                addExerciseCyclePanel.add(new JLabel(program.title()));
                addExerciseCyclePanel.add(getExplainProgramButton(program));
                addExerciseCyclePanel.add(useProgramButton(program));
                gbAdd(addExerciseCyclePanel, 0, i, 1, 1, 1, 1, jpList);
                i += 1;
            }
        }
        this.add(scrollSingle);

        this.setVisible(false);
        this.setVisible(true);
    }

    private JButton getExplainProgramButton(Program program) {
        JButton button = new JButton("프로그램 보기");
        button.addActionListener(e -> {
            this.removeAll();

            RunProgramPanel runProgramPanel = new RunProgramPanel(program);
            this.add(runProgramPanel);

            this.setVisible(false);
            this.setVisible(true);
        });
        return button;
    }

    private JButton useProgramButton(Program program) {
        JButton button = new JButton("프로그램 사용하기");
        button.addActionListener(e -> {
            Program program1 = new Program(program.title(), program.dailyPlans(), 0, program.userName()
                    , user.id(), program.id(), program.status());

            programs.add(program1);

            savePrograms();

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
}
