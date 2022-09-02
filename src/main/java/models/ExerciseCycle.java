package models;

public class ExerciseCycle {
    private String exercise;
    private int set;
    private int reps;
    private String status;
    private int dailyPlanId;

    public ExerciseCycle(String exercise, int set, int reps, String status, int dailyPlanId) {
        this.exercise = exercise;
        this.set = set;
        this.reps = reps;
        this.status = status;
        this.dailyPlanId = dailyPlanId;
    }

    public String exercise() {
        return exercise;
    }

    public int set() {
        return set;
    }

    public int reps() {
        return reps;
    }

    public void deleted() {
        status = "deleted";
    }

    public String status() {
        return status;
    }

    public int dailyPlanId() {
        return dailyPlanId;
    }

    public String toCsvRow() {
        return exercise + "," + set + "," + reps + "," + status + "," + dailyPlanId;
    }
}
