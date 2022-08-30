package models;

public class ExerciseCycle {
    private Exercise exercise;
    private int set;
    private int reps;
    private String status;

    public ExerciseCycle(Exercise exercise, int set, int reps, String status) {
        this.exercise = exercise;
        this.set = set;
        this.reps = reps;
        this.status = status;
    }

    public Exercise exercise() {
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
}
