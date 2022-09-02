package models;

public class Comment {
    private static final String DELETED = "DELETED";

    private String text;
    private String userName;
    private int postId;
    private int userId;
    private String status;

    public Comment(String text, String userName, int postId, int userId, String status) {
        this.text = text;
        this.userName = userName;
        this.postId = postId;
        this.userId = userId;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public String userName() {
        return userName;
    }

    public int postId() {
        return postId;
    }

    public int userId() {
        return userId;
    }

    public String status() {
        return status;
    }

    public String toCsvRow() {
        return text + "," + userName + "," + postId + "," + userId + "," + status;
    }

    public void delete() {
        status = "DELETED";
    }

    public void updateUserName() {
        userName = "삭제된 계정";
    }
}
