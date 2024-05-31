package comp3350.goodhabits.Presentation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import comp3350.goodhabits.Logic.ProfileManager;
import comp3350.goodhabits.Logic.RatingManager;
import comp3350.goodhabits.Objects.Profile;
import comp3350.goodhabits.R;

public class ProfileActivity extends AppCompatActivity {
    Profile profile;
    RatingBar ratingBar;
    TextView ratingNum;
    RatingManager ratingManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Текст в строке заголовка для этого действия
        setTitle("Профиль");

        // Нажав кнопку "Назад" на панели действий, вы перейдете на главный экран
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ratingManager = new RatingManager();

        ratingBar = (RatingBar) findViewById(R.id.rating_bar); // установите рейтинговую планку
        ratingBar.setRating(ratingManager.getRating());

        ratingNum = (TextView) findViewById(R.id.rating_num);
        ratingNum.setText(String.valueOf(ratingBar.getRating()));

        profile = ProfileManager.getProfileStorage(); // получение непостоянной информации профиля
        String[] profileInfo = {profile.getName(), profile.getEmail()}; // помещение информации в строковый массив
        fillProfileActivity(profileInfo);
    }

    // Функция для заполнения всех текстовых полей имени и электронной почты в этом упражнении
    public void fillProfileActivity(String [] profileInfo){
        TextView name = (TextView)findViewById(R.id.name_view);
        name.setText(profileInfo[0]);

        TextView email = (TextView)findViewById(R.id.email_view);
        email.setText(profileInfo[1]);
    }

    //Функция делегирования, которая распознает нажатие кнопки "Назад" для этого действия
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
