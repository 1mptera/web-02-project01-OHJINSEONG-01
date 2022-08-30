package models;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseCycleTest {
    @Test
    void Creation() {
        ExerciseCycle exerciseCycle = new ExerciseCycle(new Exercise("벤치프레스", "가슴"), 3, 12,"added");

        assertEquals(new Exercise("벤치프레스", "가슴"), exerciseCycle.exercise());
        assertEquals(3, exerciseCycle.set());
        assertEquals(12, exerciseCycle.reps());
    }

}