package com.example.goodhabit;

        import org.junit.Before;
        import org.junit.Test;
        import org.mockito.Mockito;
        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertTrue;

        import comp3350.goodhabits.Logic.HabitManager;
        import comp3350.goodhabits.Objects.Habit;
        import comp3350.goodhabits.Persistence.SQLite.HabitSQLite;
        import comp3350.goodhabits.Presentation.HomeActivity;


public class HabitSqlTest {
    HabitSQLite dbHelper;

    @Before
    public void setUp(){
        HomeActivity activity = new HomeActivity();
        HabitManager.createDB(new HabitSQLite(activity));
        dbHelper = Mockito.mock(HabitSQLite.class);
    }

    @Test
    public void checkAdd() {
        Habit habit = new Habit(121, "Пиши код каждое утро", true, "Кодирование ранним утром повышает производительность труда.", 6, 30, "25/05/2024", "05/06/2024", 10);
        Mockito.when(dbHelper.addHabit(habit)).thenReturn(true);
        assertTrue(dbHelper.addHabit(habit));
    }

    @Test
    public void checkDelete(){
        Habit habit = new Habit(122, "Пиши код каждое утро", true, "Кодирование ранним утром повышает производительность труда.", 6, 30, "25/05/2024", "05/06/2024", 10);
        dbHelper.addHabit(habit);
        Mockito.when(dbHelper.deleteHabit(habit)).thenReturn(true);
        assertTrue(dbHelper.deleteHabit(habit));
    }

    @Test
    public void checkHabitListSize(){
        Habit habit = new Habit(123, "Пиши код каждое утро", true, "Кодирование ранним утром повышает производительность труда.", 6, 30, "25/05/2024", "05/06/2024", 10);
        dbHelper.addHabit(habit);
        Mockito.when(dbHelper.getHabitListSize()).thenReturn(1);
        assertEquals(dbHelper.getHabitListSize(), 1);
    }

    @Test
    public void checkDeleteHabitByIndex(){
        Habit habit = new Habit(124, "Пиши код каждое утро", true, "Кодирование ранним утром повышает производительность труда.", 6, 30, "25/05/2024", "05/06/2024", 10);
        dbHelper.addHabit(habit);
        habit = new Habit(125, "Пиши код каждое утро", true, "Кодирование ранним утром повышает производительность труда.", 6, 30, "25/05/2024", "05/06/2024", 10);
        dbHelper.addHabit(habit);
        Mockito.when(dbHelper.deleteHabitByIndex(1)).thenReturn(true);
        assertTrue(dbHelper.deleteHabitByIndex(1));
    }

    @Test
    public  void checkMakeListEmpty(){
        Habit habit = new Habit(126, "Пиши код каждое утро", true, "Кодирование ранним утром повышает производительность труда.", 6, 30, "25/05/2024", "05/06/2024", 10);
        dbHelper.addHabit(habit);
        habit = new Habit(127, "Пиши код каждое утро", true, "Кодирование ранним утром повышает производительность труда.", 6, 30, "25/05/2024", "05/06/2024", 10);
        dbHelper.addHabit(habit);
        Mockito.when(dbHelper.getHabitListSize()).thenReturn(2);
        assertEquals(dbHelper.getHabitListSize(), 2);
        dbHelper.makeHabitListEmpty();
        Mockito.when(dbHelper.getHabitListSize()).thenReturn(0);
        assertEquals(dbHelper.getHabitListSize(), 0);
    }

}
