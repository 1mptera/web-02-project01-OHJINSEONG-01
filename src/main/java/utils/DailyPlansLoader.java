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
import models.ExerciseCycle;

public class DailyPlansLoader {
    public List<DailyPlan> load(List<ExerciseCycle> exerciseCycles) throws FileNotFoundException {
        List<DailyPlan> dailyPlans = new ArrayList<>();

        List<ExerciseCycle> copy = new ArrayList<>();

        File file = new File("loadDailyPlans.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");

            for (ExerciseCycle exerciseCycle : exerciseCycles) {
                if (Integer.parseInt(words[3]) == exerciseCycle.dailyPlanId()) {
                    copy.add(exerciseCycle);
                }
            }

            DailyPlan dailyPlan = new DailyPlan(copy, words[0], words[1], Integer.parseInt(words[2]),
                    Integer.parseInt(words[3]));

            dailyPlans.add(dailyPlan);
        }
        return dailyPlans;
    }

    public void save(List<DailyPlan> dailyPlans) throws IOException {
        FileWriter fileWriter = new FileWriter("loadDailyPlans.csv");

        for (DailyPlan dailyPlan : dailyPlans) {
            String line = dailyPlan.toCsvRow();

            fileWriter.write(line + "\n");
        }
        fileWriter.close();
    }

    public int createId() throws FileNotFoundException {
        List<Integer> ids = new ArrayList<>();

        File file = new File("loadDailyPlans.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");
            int id = Integer.parseInt(words[3]);

            ids.add(id);
        }
        int createdId = ids.isEmpty() ? -1 : Collections.max(ids) + 1;

        return createdId;
    }
}
