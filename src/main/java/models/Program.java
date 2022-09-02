package models;

import java.util.List;

public class Program {
    public static final String CREATED = "CREATED";
    public static final String DELETED = "DELETED";
    public static final String SHARED = "SHARED";

    private String title;
    private List<DailyPlan> dailyPlans;
    private int populurity;
    private String userName;
    private int userId;
    private int id;
    private String status;

    public Program(String title, List<DailyPlan> dailyPlans, int populurity, String userName, int userId, int id, String status) {
        this.title = title;
        this.dailyPlans = dailyPlans;
        this.populurity = populurity;
        this.userName = userName;
        this.userId = userId;
        this.id = id;
        this.status = status;
    }

    public String title() {
        return title;
    }

    public String userName() {
        return userName;
    }

    public List<DailyPlan> dailyPlans() {
        return dailyPlans;
    }

    public String toCsvRow() {
        return title + "," + populurity + "," + userName + "," + userId + "," + id + "," + status;
    }

    public int id() {
        return id;
    }

    public int populurity() {
        return populurity;
    }

    public int userId() {
        return userId;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateList(List<DailyPlan> copy) {
        dailyPlans = copy;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }

    public void delete() {
        status = "DELETED";
    }
}
