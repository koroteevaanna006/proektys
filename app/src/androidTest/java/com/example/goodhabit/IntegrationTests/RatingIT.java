package com.example.goodhabit.IntegrationTests;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.goodhabits.Logic.HabitManager;
import comp3350.goodhabits.Logic.Notifier;
import comp3350.goodhabits.Logic.RatingManager;
import comp3350.goodhabits.Objects.Habit;
import comp3350.goodhabits.Persistence.SQLite.HabitSQLite;

import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class RatingIT {
    RatingManager ratingManager;

    @Before
    public void setUp(){
        ratingManager = new RatingManager();
        Context habitContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Notifier.setNotifier(habitContext);
        HabitManager.createDB(new HabitSQLite(habitContext));
        HabitManager.addHabit(new Habit(1,"Курение", false, "Курение вызывает рак..", 11, 30, "25/05/2024", "27/05/2024", 15));
        HabitManager.addHabit(new Habit(2,"Пить воду", true, "Мне нужно увлажнить свое тело.", 10, 30, "25/05/2024", "27/05/2024", 34));
        HabitManager.addHabit(new Habit(3,"Делать зарядку", true, "Нужно оставаться в форме.", 8, 0, "27/05/2024", "06/06/2024", 2));
    }

    @Test
    public void getRating(){
        assertTrue(ratingManager.getRating() > 0);
     }

    @After
    public void tearDown() {
        HabitManager.makeHabitListEmpty();
    }
}
