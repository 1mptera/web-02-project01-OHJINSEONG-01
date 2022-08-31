package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import models.ExerciseCycle;

public class ExerciseCyclesLoader {
    public List<ExerciseCycle> load() throws FileNotFoundException {
        List<ExerciseCycle> exerciseCycles = new ArrayList<>();

        File file = new File("loadExerciseCycles.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");

            ExerciseCycle exerciseCycle = new ExerciseCycle(words[0], Integer.parseInt(words[1])
                    , Integer.parseInt(words[2]), words[3], Integer.parseInt(words[4]));

            exerciseCycles.add(exerciseCycle);
        }
        return exerciseCycles;
    }

    public void save(List<ExerciseCycle> exerciseCycles) throws IOException {
        FileWriter fileWriter = new FileWriter("loadExerciseCycles.csv");

        for (ExerciseCycle exerciseCycle : exerciseCycles) {
            String line = exerciseCycle.toCsvRow();

            fileWriter.write(line + "\n");
        }
        fileWriter.close();
    }

    public int createId() throws FileNotFoundException {
        List<Integer> ids = new ArrayList<>();

        File file = new File("loadExerciseCycles.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");
            int id = Integer.parseInt(words[6]);

            ids.add(id);
        }
        int createdId = ids.isEmpty() ? -1 : Collections.max(ids) + 1;

        return createdId;
    }
}
