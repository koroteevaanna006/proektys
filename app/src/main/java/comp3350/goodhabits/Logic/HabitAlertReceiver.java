package comp3350.goodhabits.Logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class HabitAlertReceiver extends BroadcastReceiver {
    //Name tags
    private String habitName="Название привычки";
    private String habitMsg="Сообщение о привычке";
    private String habitId="Идентификатор привычки";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper alarm = new NotificationHelper(context);
        //Получите информацию о своей привычке
        String title = intent.getStringExtra(habitName);
        String content = intent.getStringExtra(habitMsg);
        int id = intent.getIntExtra(habitId,0);
        //Проверьте, существует ли эта привычка
        if(HabitManager.checkHabit(id))
        {
            NotificationCompat.Builder nb = alarm.createHabitNotification(title,content);
            alarm.getManager().notify(id,nb.build());
        }
    }
}