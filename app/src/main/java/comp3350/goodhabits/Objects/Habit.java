package comp3350.goodhabits.Objects;

import java.io.Serializable;

// Это класс для объекта Habit
public class Habit implements Serializable {
    private final int id;
    private final String name; // Название привычки
    private final boolean type; // Тип привычки, если это так, то это "Хорошая привычка", в противном случае это "Плохая привычка".
    private final String msg; // Сообщение, которое пользователь пишет для того, чтобы выполнить какое-либо действие
    private int hour; //  выделять время дня для этой привычки
    private int minute; // распоряжаться каждой минутой часа
    private final String startDate;
    private final String endDate;
    private int daysCheckedIn;

    public Habit(int id, String name, boolean type, String msg, int hour, int minute, String startDate, String endDate, int daysCheckedIn)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.msg = msg;
        this.hour = hour;
        this.minute = minute;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysCheckedIn = daysCheckedIn;
    }

    public int getId() { return this.id; }

    public String getHabitName(){
        return this.name;
    }

    public boolean getHabitType() {
        return this.type;
    }

    public String getHabitMsg() {
        return this.msg;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public int getDaysCheckedIn() { return this.daysCheckedIn; }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute){
        this.minute = minute;
    }

    public void increaseCheckIn(){
        this.daysCheckedIn += 1;
    }
}
