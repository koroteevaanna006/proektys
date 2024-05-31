package comp3350.goodhabits.Presentation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import comp3350.goodhabits.Logic.HabitManager;

import comp3350.goodhabits.R;

public class AllHabitsActivity extends AppCompatActivity {

    private ArrayAdapter <String>  arrayAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_habits);

        Intent intent = new Intent(this, DetailActivity.class);
        ListView listView = (ListView) findViewById(R.id.listview);

        try {
            arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, HabitManager.getAllHabitNames());
            listView.setAdapter(arrayAdapter1);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Отображать всплывающее сообщение при щелчке по строке в виде списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                intent.putExtra("индекс", i);
                startActivity(intent);
            }
        });

        // Когда пользователь долго нажимает на какую-либо привычку, появляется возможность удалить ее
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(AllHabitsActivity.this)
                        .setTitle("Удалить")
                        .setMessage("Ты точно хочешь удалить эту привычку?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                HabitManager.deleteHabitByIndex(position);

                                //обновить страницу сведений
                                arrayAdapter1.notifyDataSetChanged();

                                // startActivity(новое намерение(AllHabitsActivity.this, AllHabitsActivity.class));
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton("Нет",null)
                        .show();
                return true;
            }
        });

        // Текст в строке заголовка для этого действия
        setTitle("все привычки");

        // Нажав кнопку "Назад" на панели действий, вы перейдете на главный экран
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // Функция делегирования, которая распознает нажатие кнопки "Назад" для этого действия
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
