package com.example.goodhabit.IntegrationTests;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.goodhabits.Logic.HabitManager;
import comp3350.goodhabits.Logic.Notifier;
import comp3350.goodhabits.Objects.Habit;
import comp3350.goodhabits.Persistence.SQLite.HabitSQLite;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class HabitDatabaseIT {

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Notifier.setNotifier(context);
        HabitManager.createDB(new HabitSQLite(context));
        HabitManager.addHabit(new Habit(1, "Курение", false, "Курение вызывает рак.", 11, 30, "25/05/2024", "27/05/2024", 15));
        HabitManager.addHabit(new Habit(2, "Пить воду", true, "Мне нужно увлажнить свое тело.", 10, 30, "25/05/2024", "27/05/2024", 34));
        HabitManager.addHabit(new Habit(3, "Делать зарядку", true, "Нужно оставаться в форме.", 8, 0, "27/05/2024", "06/06/2024", 2));

    }

    @Test
    public void habitList(){
        assertEquals(3, HabitManager.getHabitList().size());
    }

    @Test
    public void addHabit(){
        Habit habit = new Habit(4,"тестируется",true,"нужен тест",8,10,"25/05/2024","06/06/2024", 2);
        assertTrue(HabitManager.addHabit(habit));
        assertTrue(HabitManager.checkHabit(4));
    }

    @Test
    public void updateHabit(){
        Habit habit = new Habit( 5,"тестирование обновлений",true,"нужно сделать тестовые обновления",8,10,"27/05/2024","06/06/2024", 2);
        HabitManager.addHabit(habit);
        habit.setHour(3);
        assertTrue(HabitManager.updateHabit(habit));
        assertEquals(3, HabitManager.getHabitById(5).getHour());
    }

    @Test
    public void checkHabit(){
        Habit habit = new Habit( 6,"тестирование checkHabit",true,"нужно сделать тестовую проверку",8,10,"27/05/2024","06/06/2024", 2);
        HabitManager.addHabit(habit);
        assertTrue(HabitManager.checkHabit(6));
    }

    @Test
    public void allHabitNames(){
        String[] nameExpect = new String[HabitManager.getHabitList().size()];
        nameExpect[0] = "Курение";
        nameExpect[1] = "Пить воду";
        nameExpect[2] = "Делать зарядку";

        try {
            assertArrayEquals(nameExpect, HabitManager.getAllHabitNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteHabit(){
        Habit habit = new Habit( 7,"тестовое удаление",true,"нужно сделать тестовое удаление",8,10,"25/05/2024","06/06/2024", 2);
        HabitManager.addHabit(habit);
        assertTrue(HabitManager.deleteHabit(habit));
    }

    @Test
    public void habitListSize(){
        int size = HabitManager.getHabitListSize();
        Habit habit = new Habit( 8,"тестирование getHabitListSize",true,"нужно сделать тест getHabitListSize",8,10,"25/05/2024","06/06/2024", 2);
        HabitManager.addHabit(habit);
        assertEquals(size+1, HabitManager.getHabitListSize());
    }

    @Test
    public void makeListEmpty(){
        assertTrue(HabitManager.makeHabitListEmpty());
    }

    @Test
    public void deleteHabitByIndex(){
        HabitManager.deleteHabitByIndex(2);
        assertEquals(2, HabitManager.getHabitListSize());
    }

    @Test
    public void getID(){
        assertEquals(4, HabitManager.getID());
    }

    @Test
    public void totalCheckins(){
        assertEquals(51, HabitManager.getTotalCheckins());
    }

    @Test
    public void totalDaysPassed(){
        assert(HabitManager.getTotalDaysPassed() >0);
    }

    @Test
    public void totalNumGoodHabits(){
        assertEquals(2, HabitManager.getTotalNumGoodHabits());
    }

    @Test
    public void habitById(){
        Habit habit = new Habit( 15,"тест getHabitById",true,"нужен тест getHabitById",8,10,"27/05/2024","06/06/2024", 2);
        HabitManager.addHabit(habit);
        assertEquals(8, HabitManager.getHabitById(15).getHour());
    }

    @Test
    public void habitByIndex(){
        Habit habit = new Habit( 9,"тест getHabitByIndex",true,"нужен тест getHabitByIndex",8,10,"25/05/2024","07/06/2024", 3);
        HabitManager.addHabit(habit);
        assertEquals(8, HabitManager.getHabitByIndex(3).getHour());
    }

    @After
    public void tearDown() {
        HabitManager.makeHabitListEmpty();
    }
}
