package models;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    @Test
    void creation() {
        User user = new User("오진성", "950828-1111111", "남자", "ojs0828", "wlstjd12", "",0);

        assertEquals("오진성", user.name());
        assertEquals("950828-1111111", user.identifyNumber());
        assertEquals("남자", user.gender());
        assertEquals("ojs0828", user.userName());
        assertEquals("wlstjd12", user.password());
    }

    @Test
    void selectGender() {
        User user = new User("오진성", "950828-1111111", "남자", "ojs0828", "wlstjd12", "",0);
        user.selectGender("남자");

        assertEquals("남자", user.gender());

        user.selectGender("여자");

        assertEquals("여자", user.gender());

        user.selectGender("여자");
    }

    @Test
    void count() {
//        User user = new User("오진성", "950828-1111111", "남자", "ojs0828", "wlstjd12", "",0);
//        User countUser = new User();
//        countUser.counting();
//        user.updateId(countUser.count());
//
//        assertEquals(1, countUser.count());
//        assertEquals(1, user.id());
//
//        User user2 = new User("오진욱", "950828-1111111", "남자", "ojw0828", "wlsdnr12", "",0);
//        countUser.counting();
//        user2.updateId(countUser.count());
//
//        assertEquals(2, user2.id());
    }

}