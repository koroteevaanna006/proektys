package comp3350.goodhabits.Logic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import comp3350.goodhabits.Presentation.AllHabitsActivity;
import comp3350.goodhabits.R;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper{
    //Идентификатор канала уведомления
    public final String habitChannelID = "Привычный канал";

    //Название канала уведомления
    public final String habitChannelName ="Уведомление о привычке";

    //Переменная-уведомитель, используемая для обработки уведомлений
    public NotificationManager nManager;

    //Конструктор
    public NotificationHelper(Context context)
    {
        super(context);
        //Проверьте версию Android, чтобы определить, нужно ли создавать каналы
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            createNotificationChannel();
        }
    }


    //Этот метод инициализирует и возвращает уведомитель
    public NotificationManager getManager()
    {
        //Инициализируйте nManager, если он еще не был инициализирован
        if(nManager == null)
        {
            nManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return nManager;
    }

    //Этот метод создает канал уведомлений, необходимый для версии Android, превышающей значение "oreo".
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel()
    {
        //Создайте канал уведомлений для привычки и добавьте новые функции
        NotificationChannel habitChannel = new NotificationChannel(habitChannelID,habitChannelName, NotificationManager.IMPORTANCE_HIGH);
        habitChannel.enableLights(true);
        habitChannel.enableVibration(true);
        habitChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(habitChannel);

    }

    //Этот метод создает уведомление для напоминания о привычке
    public NotificationCompat.Builder createHabitNotification(String habitName, String habitMsg)
    {
        //Создайте ожидающее намерение, которое будет использоваться для открытия AllHabitsActivity при нажатии на уведомление
        Intent intent=new Intent(this, AllHabitsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(),habitChannelID)
                .setContentTitle(habitName)
                .setContentText(habitMsg)
                .setSmallIcon(R.drawable.ic_habit)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }
}
