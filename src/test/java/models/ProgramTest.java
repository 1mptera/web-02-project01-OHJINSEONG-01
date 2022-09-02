package models;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgramTest {
    @Test
    void creation() {
        Program program = new Program("상체 루틴", new ArrayList<>(), 0, "ymun92", 0, 0, "CREATED");

        assertEquals("상체 루틴", program.title());
        assertEquals(0, program.populurity());
        assertEquals("ymun92", program.userName());
        assertEquals(0, program.userId());
        assertEquals(0, program.id());
        assertEquals("CREATED", program.status());
    }

    @Test
    void toCsvRow() {
        Program program = new Program("상체 루틴", new ArrayList<>(), 0, "ymun92", 0, 0, "CREATED");

        assertEquals("상체 루틴,0,ymun92,0,0,CREATED", program.toCsvRow());
    }

    @Test
    void updateTitle() {
        Program program = new Program("", new ArrayList<>(), 0, "ymun92", 0, 0, "CREATED");
        program.updateTitle("상체루틴");

        assertEquals("상체루틴", program.title());
    }

    @Test
    void updateStatus() {
        Program program = new Program("상체 루틴", new ArrayList<>(), 0, "ymun92", 0, 0, "CREATED");
        program.updateStatus("DELETED");

        assertEquals("DELETED", program.status());
    }
}
