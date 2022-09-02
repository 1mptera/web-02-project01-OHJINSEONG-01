package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    public static final String CREATED = "CREATED";
    public static final String DELETED = "DELETED";

    public static final String USERNAME = "작성자";
    public static final String TITLE = "제목";
    public static final String TEXT = "내용";
    private String title;
    private String userName;
    private String text;
    private String status;
    private int likeCount;
    private int viewCount;
    private int userId;
    private String time;
    private int id;


    public Post(String title, String userName, String text, String status, int likeCount, int viewCount
            , int userId, String time, int Id) {
        this.title = title;
        this.userName = userName;
        this.text = text;
        this.status = status;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.userId = userId;
        this.time = time;
        id = Id;
    }

    public String title() {
        return title;
    }

    public String userName() {
        return userName;
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }

    public int userId() {
        return userId;
    }

    public String time() {
        return time;
    }

    public int id() {
        return id;
    }

    public String toCsvRow() {
        return title + "," + userName + "," + text + "," + status + "," + likeCount + "," + viewCount + "," + userId
                + "," + time + "," + id;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateText(String text) {
        this.text = text;
    }

    public void deleted() {
        status = "DELETED";
    }

    public int likeCount() {
        return likeCount;
    }

    public int viewCount() {
        return viewCount;
    }

    public void addViewCount() {
        viewCount += 1;
    }

    public void addLikeCount() {
        likeCount += 1;
    }

    public String classifyTime() {
        String classifyTime = "";

        String[] words = time.split(" ");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter compareTime = DateTimeFormatter.ofPattern("MM/dd");
        String time = now.format(compareTime);

        if (time.equals(words[0])) {
            classifyTime = words[1] + " " + words[2];
        }

        if (!time.equals(words[0])) {
            classifyTime = "      "+words[0];
        }

        return classifyTime;
    }

    public void updateUserName() {
        userName = "삭제된 계정";
    }
}
