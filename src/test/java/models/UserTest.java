package models;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    @Test
    void creation() {
        User user = new User("오진성", "950828-1111111", "남자", "ojs0828","wlstjd12","");

        assertEquals("오진성", user.name());
        assertEquals("950828-1111111", user.identifyNumber());
        assertEquals("남자", user.gender());
        assertEquals("ojs0828", user.userName());
        assertEquals("wlstjd12", user.password());
    }

    @Test
    void selectGender() {
        User user = new User("오진성", "950828-1111111", "남자", "ojs0828","wlstjd12","");
        user.selectGender("남자");

        assertEquals("남자", user.gender());

        user.selectGender("여자");

        assertEquals("여자", user.gender());

        user.selectGender("여자");
    }

}