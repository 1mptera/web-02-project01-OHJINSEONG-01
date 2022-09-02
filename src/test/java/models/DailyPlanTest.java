package models;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyPlanTest {
    @Test
    void Creation() {
        DailyPlan dailyPlan = new DailyPlan(new ArrayList<>(), "월요일", "created", 0, 0);

        assertEquals("월요일", dailyPlan.day());
        assertEquals("created", dailyPlan.status());
        assertEquals(0, dailyPlan.programId());
        assertEquals(0, dailyPlan.id());
    }

    @Test
    void delete() {
        DailyPlan dailyPlan = new DailyPlan(new ArrayList<>(), "월요일", "created", 0, 0);
        dailyPlan.delete();

        assertEquals("deleted", dailyPlan.status());
    }

    @Test
    void updateDay() {
        DailyPlan dailyPlan = new DailyPlan(new ArrayList<>(), "월요일", "created", 0, 0);
        dailyPlan.updateDay("화요일");

        assertEquals("화요일", dailyPlan.day());
    }

    @Test
    void updateStatus() {
        DailyPlan dailyPlan = new DailyPlan(new ArrayList<>(), "월요일", "created", 0, 0);
        dailyPlan.updateStatus("ㅎㅇ");

        assertEquals("ㅎㅇ", dailyPlan.status());
    }
}
