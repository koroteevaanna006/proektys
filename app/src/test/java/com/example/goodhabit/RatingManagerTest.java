package com.example.goodhabit;
import android.app.AlarmManager;
import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import comp3350.goodhabits.Logic.HabitManager;
import comp3350.goodhabits.Logic.Notifier;
import comp3350.goodhabits.Logic.RatingManager;
import comp3350.goodhabits.Objects.Habit;
import comp3350.goodhabits.Persistence.Stubs.HabitStorage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RatingManagerTest {

    RatingManager ratingManager;
    HabitManager hManager;
    HabitStorage fakeStorage;
    @Mock
    Context mockContext;
    @Mock
    AlarmManager mockAlarmManager;


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        fakeStorage=new HabitStorage();
        when(mockContext.getSystemService(Context.ALARM_SERVICE)).thenReturn(mockAlarmManager);
        Notifier.setNotifier(mockContext);
        hManager.test=true;
        hManager.createDB(fakeStorage);
        hManager.addHabit(new Habit(1,"", false, "", 11, 30, "25/05/2024", "27/05/2024", 15));
        hManager.addHabit(new Habit(2,"", true, "", 10, 30, "25/05/2024", "27/05/2024", 34));
        hManager.addHabit(new Habit(3,"", true, ".", 8, 0, "25/05/2024", "06/06/2024", 2));
        ratingManager=new RatingManager();
    }

    @After
    public void tearDown()
    {
        hManager.makeHabitListEmpty();
    }

    @Test
    public void getRatingTest()
    {
        assertTrue((int)ratingManager.getRating()==1);
    }

}
