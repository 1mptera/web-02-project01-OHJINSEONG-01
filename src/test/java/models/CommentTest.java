package models;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTest {
    @Test
    void creation() {
        Comment comment = new Comment("안녕", "ymun92", 0, 0, "CREATED");

        assertEquals("안녕", comment.text());
        assertEquals("ymun92", comment.userName());
        assertEquals(0, comment.postId());
        assertEquals(0, comment.userId());
        assertEquals("CREATED", comment.status());
    }

    @Test
    void toCsvRox() {
        Comment comment = new Comment("안녕", "ymun92", 0, 0, "CREATED");

        assertEquals("안녕,ymun92,0,0,CREATED", comment.toCsvRow());
    }

    @Test
    void delete() {
        Comment comment = new Comment("안녕", "ymun92", 0, 0, "CREATED");

        comment.delete();

        assertEquals("DELETED", comment.status());
    }

    @Test
    void updateUserName() {
        Comment comment = new Comment("안녕", "ymun92", 0, 0, "CREATED");

        comment.updateUserName();

        assertEquals("삭제된 계정", comment.userName());
    }
}