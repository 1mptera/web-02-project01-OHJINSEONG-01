package models;

import java.util.List;

public class Program {
    private String title;
    private List<DailyPlan> dailyPlans;
    private int populurity;
    private String userName;
    private int userId;

    public Program(String title, List<DailyPlan> dailyPlans, int populurity, String userName,int userId) {
        this.title = title;
        this.dailyPlans = dailyPlans;
        this.populurity = populurity;
        this.userName = userName;
        this.userId = userId;
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
}
