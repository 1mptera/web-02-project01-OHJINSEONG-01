package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class User {
    public static final String LOGIN = "LOGIN";
    public static final String LOGOUT = "LOGOUT";
    public static final String DELETED = "DELETED";

    private String name;
    private String identifyNumber;
    private String gender;
    private String userName;
    private String password;
    private String status;
    private int id;
    private int count;


    public User(String name, String identifyNumber, String gender, String userName, String password, String status, int id) {
        this.name = name;
        this.identifyNumber = identifyNumber;
        this.gender = gender;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.id = id;
    }

    public String name() {
        return name;
    }

    public String identifyNumber() {
        return identifyNumber;
    }

    public String userName() {
        return userName;
    }

    public String password() {
        return password;
    }

    public String gender() {
        return gender;
    }

    public void selectGender(String gender) {
        if (!this.gender.equals(gender)) {
            this.gender = gender;
        }
    }

    public void logIn() {
        status = "LOGIN";
    }

    public String status() {
        return status;
    }

    public String toCsvRow() {
        return name + "," + identifyNumber + "," + gender + "," + userName + "," + password + "," + status + "," + id;
    }

    public int id() {
        return id;
    }

    public void logOut() {
        status = "LOGOUT";
    }

    public int age() {
        String[] words = identifyNumber.split("-");

        String birthYear = words[0].substring(0, 4);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");
        String nowYear = now.format(dateTimeFormatter);

        int age = Integer.parseInt(nowYear) - Integer.parseInt(birthYear) + 1;

        return age;
    }

    public String birthDay() {
        String[] words = identifyNumber.split("-");

        String year = words[0].substring(0, 4);
        String month = words[0].substring(4, 6);
        String date = words[0].substring(6, 8);

        String birthDay = year + "년 " + month + "월 " + date + "일";
        return birthDay;
    }

    public void delete(List<Comment> comments, List<Post> posts) {
        status = "DELETED";
        userName = "삭제된 계정";

        for(Comment comment : comments){
            if(comment.userId() == id){
                comment.updateUserName();
            }
        }

        for(Post post : posts){
            if(post.userId() == id){
                post.updateUserName();
            }
        }
    }
}

