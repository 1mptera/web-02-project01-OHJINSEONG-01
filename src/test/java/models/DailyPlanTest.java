package models;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DailyPlanTest {
    @Test
    void Creation(){
        DailyPlan dailyPlan = new DailyPlan(new ArrayList<>(),"월요일","created");

        assertEquals("월요일",dailyPlan.day());
        assertEquals("created",dailyPlan.status());
    }

    @Test
    void delete(){
        DailyPlan dailyPlan = new DailyPlan(new ArrayList<>(),"월요일","created");
        dailyPlan.delete();

        assertEquals("deleted",dailyPlan.status());
    }

}