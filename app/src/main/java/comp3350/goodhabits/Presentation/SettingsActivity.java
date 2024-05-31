package comp3350.goodhabits.Presentation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import comp3350.goodhabits.R;

public class SettingsActivity extends AppCompatActivity {
    // Сохранение данных в SharedPreferences
    SharedPreferences sharedPreferences;
    SwitchCompat quoteSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Текст в строке заголовка для этого действия
        setTitle("Настройки");

        // Нажав кнопку "Назад" на панели действий, вы перейдете на главный экран
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferences = getSharedPreferences("Предпочтение при обмене квотами", Context.MODE_PRIVATE);
        quoteSwitch = (SwitchCompat) findViewById(R.id.quote_switch);

        String state = sharedPreferences.getString("указать", "");
        quoteSwitch.setChecked(state.equals("") || state.equals("включить"));

        quoteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            if(isChecked){
                myEdit.putString("указать", "включить");
            }
            else {
                myEdit.putString("указать", "выключить");
            }
            myEdit.apply();
        });
    }

    // Delegate function that recognises the tap on back button of this Activity
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
