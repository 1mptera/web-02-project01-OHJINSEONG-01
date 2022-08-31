package models;

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


    public Post(String title, String userName, String text, String status, int likeCount, int viewCount, int userId, String time) {
        this.title = title;
        this.userName = userName;
        this.text = text;
        this.status = status;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.userId = userId;
        this.time = time;
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

    public String toCsvRow() {
        return title + "," + userName + "," + text + "," + status + "," + likeCount + "," + viewCount + "," + userId + "," + time;
    }
}
