package comp3350.goodhabits.Logic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import comp3350.goodhabits.Objects.Habit;

public class Notifier {
    //Именные метки для перехода к новому намерению
    private static final String HABIT_NAME = "Название привычки";
    private static final String HABIT_MSG = "Сообщение о привычке";
    private static final String HABIT_ID = "Идентификатор привычки";
    private static Context context = null;

    public static void setNotifier(Context c) {
        context = c;
    }

    public static Context getContext() {
        return context;
    }

    //Этот метод настраивает повторяющееся уведомление для привычки
    public static void setHabitNotification(Habit habit)
    {
        //Экземпляр календаря, используемый для установки времени
        Calendar c = setTime(habit.getHour(), habit.getMinute());
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Намерение создается таким образом, чтобы информация о привычке могла быть отправлена получателю широковещательной передачи
        Intent intent = new Intent(context, HabitAlertReceiver.class);
        intent.putExtra(HABIT_NAME, habit.getHabitName());
        intent.putExtra(HABIT_MSG, habit.getHabitMsg());
        intent.putExtra(HABIT_ID, habit.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, habit.getId(), intent, 0);
        //Установим повторяющийся сигнал тревоги
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    //Этот метод отменяет уведомления, установленные для определенной привычки
    public static void cancelAlarm(int id)
    {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HabitAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,0);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }


    //Этот метод устанавливает время, в течение которого уведомление будет активным
    public static Calendar setTime(int hour, int min)
    {
        //Установите время для получения уведомления
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,0);
        if(c.before(Calendar.getInstance()))
        {
            c.add(Calendar.DATE, 1);
        }
        return c;
    }
}
