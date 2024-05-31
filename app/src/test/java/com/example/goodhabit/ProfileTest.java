package com.example.goodhabit;
import org.junit.Test;
import comp3350.goodhabits.Objects.Profile;
import static org.junit.Assert.assertEquals;

public class ProfileTest {
    @Test
    public void checkGetName() {
        Profile profile = new Profile("крот","krot@gmail.com");
        assertEquals("крот",profile.getName());

    }

    @Test
    public void checkGetEmail() {
        Profile profile = new Profile("крот","krot@gmail.com");
        assertEquals("krot@gmail.com",profile.getEmail());

    }

}
