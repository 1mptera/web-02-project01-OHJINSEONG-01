package models;

import java.util.List;

public class DailyPlan {
    private List<ExerciseCycle> exerciseCycles;
    private String day;
    private String status;

    public DailyPlan(List<ExerciseCycle> exerciseCycles, String day, String status) {
        this.exerciseCycles = exerciseCycles;
        this.day = day;
        this.status = status;
    }

    public String day() {
        return day;
    }

    public List<ExerciseCycle> exerciseCycles() {
        return exerciseCycles;
    }

    public String status() {
        return status;
    }

    public void delete() {
        status = "deleted";
    }
}
