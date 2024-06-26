package com.example.goodhabit;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.goodhabits.Logic.HabitManager;
import comp3350.goodhabits.Logic.Notifier;
import comp3350.goodhabits.Objects.Habit;
import comp3350.goodhabits.Persistence.Stubs.HabitStorage;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
public class HabitManagerTest {

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
        hManager.addHabit(new Habit(1,"Курение", false, "Курение вызывает рак.", 11, 30, "25/05/2024", "27/05/2024", 15));
        hManager.addHabit(new Habit(2,"Пить воду", true, "Мне нужно увлажнить свое тело.", 10, 30, "25/05/2024", "27/05/2024", 34));
        hManager.addHabit(new Habit(3,"Делать зарядку", true, "Нужно оставаться в форме.", 8, 0, "27/05/2024", "06/06/2024", 2));

    }

    @After
    public void tearDown()
    {
        hManager.makeHabitListEmpty();
    }

    @Test
    public void addHabitTest()
    {
        hManager.addHabit(new Habit(4,"тест", true, "добавить тест", 8, 0, "25/05/2024", "06/06/2024", 2));
        assertTrue(hManager.getHabitListSize()==4);
    }

    @Test
    public void checkHabitTest()
    {
        assertTrue(hManager.checkHabit(1));
        assertTrue(hManager.checkHabit(2));
    }

    @Test
    public void updateHabitTest()
    {
        Habit habit=new Habit(5,"обновить тест", true, "тест", 8, 0, "25/05/2024", "06/06/2024", 2);
        hManager.addHabit(habit);
        habit.setHour(1);
        hManager.updateHabit(habit);
        assertTrue(habit.getHour()==hManager.getHabitById(5).getHour());
    }

    @Test
    public void getDBTest()
    {
        assertNotNull(hManager.getDB());
    }

    @Test
    public void getHabitListTest()
    {
        assertTrue(hManager.getHabitList().size()==3);
    }

    @Test
    public void getAllHabitNames() throws Exception {
        assertTrue(hManager.getAllHabitNames().length==3);
    }

    @Test
    public void deleteHabitTest()
    {
        hManager.deleteHabit(hManager.getHabitById(1));
        assertTrue(hManager.getHabitListSize()==2);
    }

    @Test
    public void getHabitListSizeTest()
    {
        assertTrue(hManager.getHabitListSize()==3);
    }

    @Test
    public void makeListEmptyTest()
    {
        hManager.makeHabitListEmpty();
        assertTrue(hManager.getHabitListSize()==0);
    }

    @Test
    public void deleteHabitByIndexTest()
    {
        hManager.deleteHabitByIndex(0);
        assertFalse(hManager.checkHabit(1));
    }

    @Test
    public void getHabitByIndexTest()
    {
        assertTrue(hManager.getHabitByIndex(1).getId()==2);
    }

    @Test
    public void getIdTest()
    {
        assertTrue(hManager.getID()==4);
    }

    @Test
    public void getTotalCheckinsTest()
    {
        assertTrue(hManager.getTotalCheckins()==51);
    }

    @Test
    public void getTotalDaysPassedTest()
    {
        assertTrue(hManager.getTotalDaysPassed()>0);
    }

    @Test
    public void getTotalNumGoodHabits()
    {
        assertTrue(hManager.getTotalNumGoodHabits()==2);
    }

    @Test
    public void getHabitByidTest()
    {
        Habit habit=new Habit(5,"обновить тест", true, "тест", 8, 0, "25/05/2024", "06/06/2024", 2);
        hManager.addHabit(habit);
        assertTrue(hManager.getHabitById(5).getId()==habit.getId());
    }


}
