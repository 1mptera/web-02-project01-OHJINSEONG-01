package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.Post;

public class PostsLoader {
    public List<Post> load() throws FileNotFoundException {
        List<Post> posts = new ArrayList<>();

        File file = new File("loadPosts.csv");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] words = line.split(",");

            Post post = new Post(words[0], words[1], words[2], words[3], Integer.parseInt(words[4]),
                    Integer.parseInt(words[5]), Integer.parseInt(words[6]), words[7]);

            posts.add(post);
        }
        return posts;
    }

    public void save(List<Post> posts) throws IOException {
        FileWriter fileWriter = new FileWriter("loadPosts.csv");

        for (Post post : posts) {
            String line = post.toCsvRow();

            fileWriter.write(line + "\n");
        }
        fileWriter.close();
    }
}
