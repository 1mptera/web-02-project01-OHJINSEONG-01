package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import models.User;

public class UsersLoader {
    public List<User> load() throws FileNotFoundException {
        List<User> users = new ArrayList<>();

        File file = new File("loadUsers.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");

            User user = new User(words[0], words[1], words[2], words[3], words[4], words[5], Integer.parseInt(words[6]));

            users.add(user);
        }
        return users;
    }

    public void save(List<User> users) throws IOException {
        FileWriter fileWriter = new FileWriter("loadUsers.csv");

        for (User user : users) {
            String line = user.toCsvRow();

            fileWriter.write(line + "\n");
        }
        fileWriter.close();
    }

    public int createId() throws FileNotFoundException {
        List<Integer> ids = new ArrayList<>();

        File file = new File("loadUsers.csv");

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
