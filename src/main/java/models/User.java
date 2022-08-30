package models;

public class User {
    private String name;
    private String identifyNumber;
    private String gender;
    private String userName;
    private String password;
    private String status;


    public User(String name, String identifyNumber, String gender, String userName, String password, String status) {
        this.name = name;
        this.identifyNumber = identifyNumber;
        this.gender = gender;
        this.userName = userName;
        this.password = password;
        this.status = status;
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
        status = "logIn";
    }

    public String status() {
        return status;
    }

    public String toCsvRow() {
        return name + "," + identifyNumber + "," + gender + "," + userName + "," + password + "," + status;
    }
}

