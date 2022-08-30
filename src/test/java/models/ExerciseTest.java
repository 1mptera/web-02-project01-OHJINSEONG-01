package models;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {
    @Test
    void Creation(){
        Exercise exercise = new Exercise("벤치프레스","가슴");

        assertEquals("벤치프레스",exercise.name());
        assertEquals("가슴",exercise.type());
    }

    @Test
    void equals(){
        Exercise exercise = new Exercise("벤치프레스","가슴");

        assertEquals(exercise,new Exercise("벤치프레스","가슴"));
    }
}