package comp3350.goodhabits.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.goodhabits.Logic.HabitManager;
import comp3350.goodhabits.Logic.Notifier;
import comp3350.goodhabits.Logic.ProfileManager;
import comp3350.goodhabits.Objects.Habit;
import comp3350.goodhabits.Objects.Profile;
import comp3350.goodhabits.Persistence.SQLite.HabitSQLite;
import comp3350.goodhabits.Persistence.SQLite.ProfileSQLite;
import comp3350.goodhabits.R;

public class ProfileInputActivity extends AppCompatActivity {

    private EditText profileName;
    private EditText profileEmail;

    @Override
    protected void onResume() {
        super.onResume();

        if(Notifier.getContext() == null)
            Notifier.setNotifier(ProfileInputActivity.this);

        if(HabitManager.getDB() == null){
            //Менеджер для сохранения постоянных привычек
            HabitManager.createDB(new HabitSQLite(this));

            // Менеджер для хранения непостоянных привычек
            // HabitManager.createDB(новое хранилище привычек());
        }

        if(ProfileManager.getDB() == null){
            // Менеджер для постоянного хранения профилей
            ProfileManager.createDB(new ProfileSQLite(this));

            // Менеджер для непостоянного хранения профилей
            // ProfileManager.createDB(новое хранилище профилей());
        }

        if(HabitManager.getHabitListSize() == 0){
            HabitManager.addHabit(new Habit(1,"Курение", false, "Курение вызывает рак.", 11, 30, "13/03/2024", "18/05/2024", 15));
            HabitManager.addHabit(new Habit(2,"Пить воду", true, "Мне нужно увлажнить свое тело.", 10, 30, "13/03/2024", "18/05/2024", 34));
            HabitManager.addHabit(new Habit(3,"Делать зарядку", true, "Нужно оставаться в форме.", 8, 0, "01/04/2024", "06/06/2024", 2));
        }

        // Если profileIsSet имеет значение true, то он заполняет профиль поддельными данными и переходит непосредственно на главный экран
        if(ProfileManager.getProfileStorage() != null){
            moveToMainActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_input);

        // Текст в строке заголовка для этого действия
        setTitle("Создать профиль");

        // Извлечение имени профиля, заданного пользователем
        profileName = (EditText)findViewById(R.id.name_input);

        // Извлечение электронной почты профиля, предоставленной пользователем
        profileEmail = (EditText)findViewById(R.id.email_input);

        // Кнопка, которая проверяет информацию профиля после нажатия
        Button done = (Button) findViewById(R.id.submit_profile);
        done.setOnClickListener(v -> validateForm());
    }

    // Вспомогательная функция для перехода к основному виду деятельности
    public void moveToMainActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    // Функция для проверки формы профиля
    public void validateForm(){
        boolean pass = true;

        String name = profileName.getText().toString();
        String email = profileEmail.getText().toString();

        //Отправьте всплывающее сообщение, если поле имени пусто
        if(name.equals("") || email.equals("")){
            Toast.makeText(this, "Необходимы как имя, так и адрес электронной почты!", Toast.LENGTH_SHORT).show();
            pass = false;
        }

        // Если все поля заполнены, то сохраните профиль и перейдите на Главный экран
        if(pass){
            Profile profile = new Profile(name, email);
            ProfileManager.addToProfileStorage(profile);
            moveToMainActivity();
        }
    }
}
