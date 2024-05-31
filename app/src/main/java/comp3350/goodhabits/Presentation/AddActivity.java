package comp3350.goodhabits.Presentation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import comp3350.goodhabits.Logic.DateManager;
import comp3350.goodhabits.Logic.HabitManager;
import comp3350.goodhabits.Logic.TimeManager;
import comp3350.goodhabits.Logic.TimePickerFragment;
import comp3350.goodhabits.Objects.Habit;
import comp3350.goodhabits.R;


// Это занятие помогает выработать новую привычку
public class AddActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private String name;
    private RadioGroup typeGroup;
    private RadioButton type;
    private String msg;
    private int hour = -1; // Инициализирован значением -1
    private int minute = -1; // Инициализирован значением -1

    TimeManager time = new TimeManager();
    DateManager dateParser = new DateManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Контексты формы, которые необходимо сохранить
        EditText habitName;
        EditText habitMsg;
        Button timeButton;
        Button addButton;

        // Текст в строке заголовка для этого действия
        setTitle("Добавить привычку");

        // Нажав кнопку "Назад" на панели действий, вы перейдете на главный экран
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Получите название привычки, когда оно будет записано
        habitName = findViewById(R.id.habit_name_input);

        // Нажатие радиокнопки для определения типа привычки
        typeGroup = findViewById(R.id.habit_type_group);
        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                type = findViewById(id); // Assign the new selected button to type
            }
        });

        // Когда пользовательское сообщение будет написано
        habitMsg = findViewById(R.id.habit_message_input);

        // Нажмите кнопку выбора времени, чтобы выбрать время для выполнения действия, связанного с привычкой
        timeButton = findViewById(R.id.time_picker);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Средство выбора времени");
            }
        });

        // Нажав на кнопку "Отправить" в форме добавления сайта
        addButton = findViewById(R.id.submit_habit);
        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                name = habitName.getText().toString(); // Узнайте название привычки

                // Выберите группу типов (если пользователь не нажимает на радиокнопки).
                int radioId = typeGroup.getCheckedRadioButtonId();
                type = findViewById(radioId);
                msg = habitMsg.getText().toString(); // Получить сообщение пользователя
                validateForm(); // Проверьте наличие всплывающего сообщения

                // Если какое-либо всплывающее сообщение НЕ отображается
                if(!validateForm()) {
                    Habit newHabit = createHabit(name,type,msg,hour,minute); // Create a Habit
                    addHabit(newHabit); // Add the Habit to the list of Habits
                    startActivity(new Intent(AddActivity.this, HomeActivity.class)); // Go to the main screen
                }
            }
        });
    }

    // Эта функция находит радиокнопку, выбранную пользователем
    public void selectedButton(View v)
    {
        int radioId = typeGroup.getCheckedRadioButtonId();   // Получите идентификатор выбранной кнопки
        type = findViewById(radioId); // Присвойте его нашей переменной RadioButton
    }

    // Эта функция отображает выбранное время и сохраняет время, выбранное пользователем
    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfHour) {
        // Отображение выбранного пользователем времени в формате 12 часов для лучшего понимания
        TextView textView = (TextView)findViewById(R.id.time_picker_label);
        textView.setText(time.getTime(hourOfDay, minuteOfHour));

        // Сохраняйте выбранное пользователем время в формате 24 часа для удобства кодирования
        hour = hourOfDay;
        minute = minuteOfHour;
    }

    // Функция делегирования, которая распознает нажатие кнопки "Назад" для этого действия
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Эта функция создает новую привычку
    public Habit createHabit(String hName, RadioButton hType, String hMsg, int hTime, int mTime) {
        // A boolean variable that is true for Good_Habit and false for Bad_Habit
        boolean boolType = hType.getId() == findViewById(R.id.good_habit).getId();
        String startDate = dateParser.getTodaysDate();
        String endDate ="";
        try {
            endDate = dateParser.getEndDate(startDate);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return new Habit(HabitManager.getID(), hName, boolType, hMsg, hTime, mTime, startDate, endDate, 0);
    }

    // Эта функция добавляет новую привычку к списку привычек
    public void addHabit(Habit habit)
    {
        HabitManager.addHabit(habit);
    }

    public boolean validateForm() {
        boolean toastFired = false;

        String errorMsg="";
        // Проверьте, дал ли пользователь название привычке
        if(name == null || name.length() == 0)
        {
            errorMsg="Название привычки отсутствует!";
            toastFired = true;
        }

        // Проверьте, выбрал ли пользователь определенный тип привычки
        if(typeGroup.getCheckedRadioButtonId() == -1)
        {
            errorMsg="Название привычки отсутствует!";
            toastFired = true;
        }

        //Проверьте, выбрал ли пользователь время
        if(hour == -1 && minute == -1)
        {
            errorMsg="Время не выбрано";
            toastFired = true;
        }

        if(toastFired)
        {
            Toast.makeText(this,errorMsg,Toast.LENGTH_SHORT).show();
        }

        return toastFired;

    }


}
