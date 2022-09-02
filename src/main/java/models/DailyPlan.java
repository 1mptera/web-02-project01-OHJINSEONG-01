package models;

import java.util.List;

public class DailyPlan {
    private List<ExerciseCycle> exerciseCycles;
    private String day;
    private String status;
    private int programId;
    private int id;

    public DailyPlan(List<ExerciseCycle> exerciseCycles, String day, String status, int programId, int id) {
        this.exerciseCycles = exerciseCycles;
        this.day = day;
        this.status = status;
        this.programId = programId;
        this.id = id;
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

    public String toCsvRow() {
        return day + "," + status + "," + programId + "," + id;
    }

    public int id() {
        return id;
    }

    public void updateList(List<ExerciseCycle> copy) {
        exerciseCycles = copy;
    }

    public int programId() {
        return programId;
    }

    public void updateDay(String day) {
        this.day = day;
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}
