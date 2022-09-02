package models;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseCycleTest {
    @Test
    void Creation() {
        ExerciseCycle exerciseCycle = new ExerciseCycle("벤치 프레스", 3, 12, "added", 0);

        assertEquals("벤치 프레스", exerciseCycle.exercise());
        assertEquals(3, exerciseCycle.set());
        assertEquals(12, exerciseCycle.reps());
        assertEquals("added", exerciseCycle.status());
        assertEquals(0, exerciseCycle.dailyPlanId());
    }

    @Test
    void deleted() {
        ExerciseCycle exerciseCycle = new ExerciseCycle("벤치 프레스", 3, 12, "added", 0);

        exerciseCycle.deleted();

        assertEquals("deleted", exerciseCycle.status());
    }

    @Test
    void toCsvRow() {
        ExerciseCycle exerciseCycle = new ExerciseCycle("벤치 프레스", 3, 12, "added", 0);

        assertEquals("벤치 프레스,3,12,added,0", exerciseCycle.toCsvRow());
    }
}