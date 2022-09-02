package models;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class PostTest {
    @Test
    void creation() {
        Post post = new Post("하이", "ymun92", "하이입니다.", "CREATED", 0, 0, 0, "20:20", 0);

        assertEquals("하이", post.title());
        assertEquals("ymun92", post.userName());
        assertEquals("하이입니다.", post.text());
        assertEquals("CREATED", post.status());
        assertEquals(0, post.likeCount());
        assertEquals(0, post.viewCount());
        assertEquals(0, post.userId());
        assertEquals("20:20", post.time());
        assertEquals(0, post.id());
    }

    @Test
    void toCsvRow() {
        Post post = new Post("하이", "ymun92", "하이입니다.", "CREATED", 0, 0, 0, "20:20", 0);

        assertEquals("하이,ymun92,하이입니다.,CREATED,0,0,0,20:20,0", post.toCsvRow());
    }

    @Test
    void updateTitle() {
        Post post = new Post("하이", "ymun92", "하이입니다.", "CREATED", 0, 0, 0, "20:20", 0);
        post.updateTitle("안녕하세요");

        assertEquals("안녕하세요", post.title());
    }

    @Test
    void updatedText() {
        Post post = new Post("하이", "ymun92", "하이입니다.", "CREATED", 0, 0, 0, "20:20", 0);
        post.updateText("반갑습니다");

        assertEquals("반갑습니다", post.text());
    }

    @Test
    void deleted() {
        Post post = new Post("하이", "ymun92", "하이입니다.", "CREATED", 0, 0, 0, "20:20", 0);
        post.deleted();

        assertEquals("DELETED", post.status());
    }

    @Test
    void updateUserName() {
        Post post = new Post("하이", "ymun92", "하이입니다.", "CREATED", 0, 0, 0, "20:20", 0);
        post.updateUserName();

        assertEquals("삭제된 계정", post.userName());
    }
}