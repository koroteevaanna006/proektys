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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import comp3350.goodhabits.Presentation.AllHabitsActivity;
import comp3350.goodhabits.Presentation.DetailActivity;
import comp3350.goodhabits.Presentation.ProfileInputActivity;
import comp3350.goodhabits.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UpdateHabitST {
    @Rule
    public ActivityTestRule<ProfileInputActivity> activityTestRule = new ActivityTestRule<>(ProfileInputActivity.class);

    @Before
    public void setUp(){
        activityTestRule.finishActivity();
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
        SystemTestUtils.setProfileDB("тест", "test@gmail.com");
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void updateTime(){
        Intents.init();
        onView(withId(R.id.all_habits_button)).perform(click());
        intended(hasComponent(AllHabitsActivity.class.getName()));   // Проверяем, находимся ли мы сейчас на экране AllHabitsActivity
        onView(withText("Пить воду")).perform(click());   // Переходим к привычке пить воду
        intended(hasComponent(DetailActivity.class.getName()));  // Проверяем, находимся ли мы на экране подробной информации о привычке пить воду
        onView(withText("10:30 AM")).check(matches(isDisplayed()));   // Проверка того, отображается ли первоначальное время уведомления о привычке пить воду
        onView(withId(R.id.dv_change_time_button)).perform(click());    // Выбор времени открытия
        onView(withText("OK")).perform(click());    // Нажмите кнопку ОК, чтобы установить новое время
        onView(withText("10:30 AM")).check(doesNotExist());  // Проверка того, изменилось ли первоначальное время уведомления о привычке пить воду
        Intents.release();
    }

    @Test
    public void updateCheckIn(){
        Intents.init();
        onView(withId(R.id.all_habits_button)).perform(click());   // Нажатие кнопки AllHabits на главном экране
        intended(hasComponent(AllHabitsActivity.class.getName()));   // Проверяем, находимся ли мы сейчас на экране AllHabitsActivity
        onView(withText("Пить воду")).perform(click());   // Переходим к привычке пить воду
        intended(hasComponent(DetailActivity.class.getName()));  // Проверяем, находимся ли мы на экране подробной информации о привычке пить воду
        onView(withText("34")).check(matches(isDisplayed()));   // Проверка того, отображается ли первоначальное регистрационное количество потребляемой воды
        onView(withId(R.id.dv_check_in_button)).perform(click());   // Нажмите кнопку "РЕГИСТРАЦИЯ", чтобы увеличить количество дней регистрации
        onView(withText("35")).check(matches(isDisplayed()));// Проверка того, изменилось ли первоначальное количество питьевой воды, указанное при регистрации
        Intents.release();
    }

    @After
    public void tearDown(){
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
    }
}
