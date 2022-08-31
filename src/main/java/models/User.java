package models;

public class User {
    public static final String LOGIN = "LOGIN";
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
}

