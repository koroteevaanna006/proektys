package com.example.goodhabit;

import comp3350.goodhabits.Logic.DateManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {
    private DateManager dateParser;
    private String startDate;
    private String currDate;

    @Before
    public void setUp(){
        dateParser = new DateManager();
        startDate = "25/05/2024";
        currDate = "27/05/2024";
    }

    @Test
    public void checkEndDate(){
        try {
            String date = dateParser.getEndDate(startDate);
            assertEquals("25/05/2024", date);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void checkDaysPassed(){
        try {
            int days = dateParser.getDaysPassed(startDate, currDate);
            assertEquals("11", String.valueOf(days));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
