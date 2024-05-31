package com.example.goodhabit.SystemTests;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.goodhabits.Presentation.AddActivity;
import comp3350.goodhabits.Presentation.HomeActivity;
import comp3350.goodhabits.Presentation.ProfileInputActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import comp3350.goodhabits.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddHabitST {
    @Rule
    public ActivityTestRule<ProfileInputActivity> activityTestRule = new ActivityTestRule<>(ProfileInputActivity.class);

    @Before
    public void setUp(){
        activityTestRule.finishActivity();
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
        SystemTestUtils.setProfileDB("тест", "test@gmail.com");  // для обходного ввода профиля
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void addHabit(){
        Intents.init();
        onView(withText("3")).check(matches(isDisplayed()));    // Общее количество привычек на главном экране
        onView(withText("4")).check(doesNotExist());    // Убедитесь, что у вас есть ровно 3 привычки
        onView(withId(R.id.add_button)).perform(click());   //Нажатие кнопки "Добавить" на главном экране
        intended(hasComponent(AddActivity.class.getName()));    // Проверяем, находимся ли мы сейчас в режиме AddActivity
        onView(withId(R.id.habit_name_input)).perform(typeText("Системный тест")); // Название привычки ввода текста
        onView(withId(R.id.good_habit)).perform(click());   // Assigning Habit type
        onView(withId(R.id.habit_message_input)).perform(typeText("Это сообщение предназначено для проверки системы"));    // Сообщение о привычке набирать текст
        onView(withId(R.id.time_picker)).perform(scrollTo()).perform(click());  // Opening TimePicker
        onView(withText("OK")).perform(click());    // Определение времени привычки
        onView(withId(R.id.submit_habit)).perform(click()); // Нажатие кнопки "Добавить"
        intended(hasComponent(HomeActivity.class.getName()));   // Проверяем, находимся ли мы сейчас в режиме домашней активности
        onView(withText("4")).check(matches(isDisplayed()));    // Общее количество привычек на главном экране теперь равно 3+1= 4
        Intents.release();
    }

    @After
    public void tearDown(){
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
    }
}
