package models;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    @Test
    void creation() {
        User user = new User("오진성", "19950828-1111111", "남자", "ojs0828", "wlstjd12", "CREATED", 0);

        assertEquals("오진성", user.name());
        assertEquals("19950828-1111111", user.identifyNumber());
        assertEquals("남자", user.gender());
        assertEquals("ojs0828", user.userName());
        assertEquals("wlstjd12", user.password());
    }

    @Test
    void selectGender() {
        User user = new User("오진성", "19950828-1111111", "남자", "ojs0828", "wlstjd12", "CREATED", 0);
        user.selectGender("남자");

        assertEquals("남자", user.gender());

        user.selectGender("여자");

        assertEquals("여자", user.gender());

        user.selectGender("여자");
    }

    @Test
    void logIn() {
        User user = new User("오진성", "19950828-1111111", "남자", "ojs0828", "wlstjd12", "CREATED", 0);
        user.logIn();

        assertEquals("LOGIN", user.status());
    }

    @Test
    void logOut() {
        User user = new User("오진성", "19950828-1111111", "남자", "ojs0828", "wlstjd12", "CREATED", 0);
        user.logOut();

        assertEquals("LOGOUT", user.status());
    }

    @Test
    void toCsvRow() {
        User user = new User("오진성", "19950828-1111111", "남자", "ojs0828", "wlstjd12", "CREATED", 0);

        assertEquals("오진성,19950828-1111111,남자,ojs0828,wlstjd12,CREATED,0", user.toCsvRow());
    }

    @Test
    void age() {
        User user = new User("오진성", "19950828-1111111", "남자", "ojs0828", "wlstjd12", "CREATED", 0);

        assertEquals(28, user.age());
    }

    @Test
    void birthday() {
        User user = new User("오진성", "19950828-1111111", "남자", "ojs0828", "wlstjd12", "CREATED", 0);

        assertEquals("1995년 08월 28일", user.birthDay());
    }

    @Test
    void delete() {
        User user = new User("오진성", "19950828-1111111", "남자", "ojs0828", "wlstjd12", "CREATED", 0);
        List<Comment> comments = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        Comment comment = new Comment("안녕", "ymun92", 0, 0, "CREATED");
        comments.add(comment);

        Post post = new Post("하이", "ymun92", "하이입니다.", "CREATED", 0, 0, 0, "20:20", 0);
        posts.add(post);

        user.delete(comments,posts);

        assertEquals("삭제된 계정", user.userName());
        assertEquals("삭제된 계정", comment.userName());
        assertEquals("삭제된 계정", post.userName());
    }
}
