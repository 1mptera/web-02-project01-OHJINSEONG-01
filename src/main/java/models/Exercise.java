package models;

public class Exercise {
    private String name;
    private String type;

    public Exercise(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        Exercise otherExercise = (Exercise) other;
        return this.name.equals(otherExercise.name)
                && this.type.equals(otherExercise.type);
    }
}
