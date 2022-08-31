package models;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class PostTest {
    @Test
    void creation() {
        Post post = new Post("하이", "ymun92", "하이입니다.", "created", 0,0,0, time);

        assertEquals("하이",post.title());
        assertEquals("ymun92",post.userName());
        assertEquals("하이입니다",post.text());
        assertEquals("created",post.status());
        assertEquals(0,post.userId());
    }

}