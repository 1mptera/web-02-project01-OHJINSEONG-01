package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import models.DailyPlan;
import models.Program;

public class ProgramsLoader {
    public List<Program> load(List<DailyPlan> dailyPlans) throws FileNotFoundException {
        List<Program> programs = new ArrayList<>();

        List<DailyPlan> copy = new ArrayList<>();

        File file = new File("loadPrograms.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");

            for (DailyPlan dailyPlan : dailyPlans) {
                if (Integer.parseInt(words[4]) == dailyPlan.programId()) {
                    copy.add(dailyPlan);
                }
            }

            Program program = new Program(words[0], copy, Integer.parseInt(words[1]), words[2],
                    Integer.parseInt(words[3]), Integer.parseInt(words[4]), words[5]);

            programs.add(program);
        }
        return programs;
    }

    public void save(List<Program> programs) throws IOException {
        FileWriter fileWriter = new FileWriter("loadPrograms.csv");

        for (Program program : programs) {
            String line = program.toCsvRow();

            fileWriter.write(line + "\n");
        }
        fileWriter.close();
    }

    public int createId() throws FileNotFoundException {
        List<Integer> ids = new ArrayList<>();

        File file = new File("loadPrograms.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");
            int id = Integer.parseInt(words[4]);

            ids.add(id);
        }
        int createdId = ids.isEmpty() ? -1 : Collections.max(ids) + 1;

        return createdId;
    }
}
