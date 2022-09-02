package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.Comment;

public class CommentsLoader {
    public List<Comment> load() throws FileNotFoundException {
        List<Comment> comments = new ArrayList<>();

        File file = new File("loadComments.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");

            Comment comment = new Comment(words[0], words[1], Integer.parseInt(words[2]), Integer.parseInt(words[3])
                    , words[4]);

            comments.add(comment);
        }
        return comments;
    }

    public void save(List<Comment> comments) throws IOException {
        FileWriter fileWriter = new FileWriter("loadComments.csv");

        for (Comment comment : comments) {
            String line = comment.toCsvRow();

            fileWriter.write(line + "\n");
        }
        fileWriter.close();
    }
}
