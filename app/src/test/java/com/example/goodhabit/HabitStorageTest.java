package com.example.goodhabit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

import comp3350.goodhabits.Logic.HabitManager;
import comp3350.goodhabits.Objects.Habit;
import comp3350.goodhabits.Persistence.Stubs.HabitStorage;

public class HabitStorageTest {
    HabitStorage fakeHabitStorage;

    @Before
    public void setUp()
    {
        fakeHabitStorage=new HabitStorage();
        fakeHabitStorage.addHabit(new Habit(1,"Курение", false, "Курение вызывает рак.", 11, 30, "25/05/2024", "27/05/2024", 15));
        fakeHabitStorage.addHabit(new Habit(2,"Пить воду", true, "Мне нужно увлажнить свое тело.", 10, 30, "25/05/2024", "27/05/2024", 34));
        fakeHabitStorage.addHabit(new Habit(3,"Делать зарядку", true, "Нужно оставаться в форме.", 8, 0, "25/05/2024", "06/06/2024", 2));


    }

    @After
    public void tearDown()
    {
        fakeHabitStorage.makeHabitListEmpty();
    }

    @Test
    public void getHabitListTest()
    {
        ArrayList<Habit> list=fakeHabitStorage.getHabitList();
        assert (list.size()==3);
    }

    @Test
    public void addHabitTest()
    {
        Habit newHabit=new Habit(50,"Проводите тестирование", true, "Любой тест - это хороший тест", 11, 30, "25/05/2024", "27/05/2024", 15);
        fakeHabitStorage.addHabit(newHabit);
        assertNotNull(fakeHabitStorage.getHabitById(50));
    }

    @Test
    public void updateHabitTest()
    {
        Habit newHabit=new Habit(55,"Тестирование обновлений", true, "Любой тест - это хороший тест", 11, 30, "25/05/2024", "27/05/2024", 15);
        fakeHabitStorage.addHabit(newHabit);
        newHabit.setHour(7);
        fakeHabitStorage.updateHabit(newHabit);
        assert (fakeHabitStorage.getHabitById(55).getHour()==7);
    }

    @Test
    public void deleteHabitTest()
    {
        Habit newHabit=new Habit(60,"Delete Testing", true, "Any test is a good test", 11, 30, "13/03/2020", "18/05/2020", 15);
        fakeHabitStorage.addHabit(newHabit);
        fakeHabitStorage.deleteHabit(newHabit);
        assertNull(fakeHabitStorage.getHabitById(60));
    }


    @Test
    public void getHabitListSizeTest()
    {
        assert (fakeHabitStorage.getHabitListSize()>0);
    }

    @Test
    public void deleteHabitByIndexTest()
    {
        int old=fakeHabitStorage.getHabitListSize();
        fakeHabitStorage.deleteHabitByIndex(0);
        int neww=fakeHabitStorage.getHabitListSize();
        assert(old>neww);
    }

    @Test
    public void getAllHabitNamesTest()
    {
        try{
            String[] list=fakeHabitStorage.getAllHabitNames();
            assert (list.length>0);
        }
        catch (Exception e)
        {
            e.toString();
        }
    }


}

